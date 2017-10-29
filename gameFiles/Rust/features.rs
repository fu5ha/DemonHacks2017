

use std::env;

use util::errors::CargoResult;

enum Status {
    Stable,
    Unstable,
}

macro_rules! features {
    (
        pub struct Features {
            $([$stab:ident] $feature:ident: bool,)*
        }
    ) => (
        #[derive(Default, Clone, Debug)]
        pub struct Features {
            $($feature: bool,)*
            activated: Vec<String>,
        }

        impl Feature {
            $(
                pub fn $feature() -> &'static Feature {
                    fn get(features: &Features) -> bool {
                        features.$feature
                    }
                    static FEAT: Feature = Feature {
                        name: stringify!($feature),
                        get: get,
                    };
                    &FEAT
                }
            )*

            fn is_enabled(&self, features: &Features) -> bool {
                (self.get)(features)
            }
        }

        impl Features {
            fn status(&mut self, feature: &str) -> Option<(&mut bool, Status)> {
                if feature.contains("_") {
                    return None
                }
                let feature = feature.replace("-", "_");
                $(
                    if feature == stringify!($feature) {
                        return Some((&mut self.$feature, stab!($stab)))
                    }
                )*
                None
            }
        }
    )
}

macro_rules! stab {
    (stable) => (Status::Stable);
    (unstable) => (Status::Unstable);
}

/// A listing of all features in Cargo
///
/// "look here"
///
/// This is the macro that lists all stable and unstable features in Cargo.
/// You'll want to add to this macro whenever you add a feature to Cargo, also
/// following the directions above.
///
/// Note that all feature names here are valid Rust identifiers, but the `_`
/// character is translated to `-` when specified in the `cargo-features`
/// manifest entry in `Cargo.toml`.
features! {
    pub struct Features {

        // A dummy feature that doesn't actually gate anything, but it's used in
        // testing to ensure that we can enable stable features.
        [stable] test_dummy_stable: bool,

        // A dummy feature that gates the usage of the `im-a-teapot` manifest
        // entry. This is basically just intended for tests.
        [unstable] test_dummy_unstable: bool,

        // Downloading packages from alternative registry indexes.
        [unstable] alternative_registries: bool,
    }
}

pub struct Feature {
    name: &'static str,
    get: fn(&Features) -> bool,
}

impl Features {
    pub fn new(features: &[String],
               warnings: &mut Vec<String>) -> CargoResult<Features> {
        let mut ret = Features::default();
        for feature in features {
            ret.add(feature, warnings)?;
            ret.activated.push(feature.to_string());
        }
        Ok(ret)
    }

    fn add(&mut self, feature: &str, warnings: &mut Vec<String>) -> CargoResult<()> {
        let (slot, status) = match self.status(feature) {
            Some(p) => p,
            None => bail!("unknown cargo feature `{}`", feature),
        };

        if *slot {
            bail!("the cargo feature `{}` has already been activated", feature);
        }

        match status {
            Status::Stable => {
                let warning = format!("the cargo feature `{}` is now stable \
                                       and is no longer necessary to be listed \
                                       in the manifest", feature);
                warnings.push(warning);
            }
            Status::Unstable if !nightly_features_allowed() => {
                bail!("the cargo feature `{}` requires a nightly version of \
                       Cargo, but this is the `{}` channel",
                      feature,
                      channel())
            }
            Status::Unstable => {}
        }

        *slot = true;

        Ok(())
    }

    pub fn activated(&self) -> &[String] {
        &self.activated
    }

    pub fn require(&self, feature: &Feature) -> CargoResult<()> {
        if feature.is_enabled(self) {
            Ok(())
        } else {
            let feature = feature.name.replace("_", "-");
            let mut msg = format!("feature `{}` is required", feature);

            if nightly_features_allowed() {
                let s = format!("\n\nconsider adding `cargo-features = [\"{0}\"]` \
                                 to the manifest", feature);
                msg.push_str(&s);
            } else {
                let s = format!("\n\n\
                    this Cargo does not support nightly features, but if you\n\
                    switch to nightly channel you can add\n\
                    `cargo-features = [\"{}\"]` to enable this feature",
                    feature);
                msg.push_str(&s);
            }
            bail!("{}", msg);
        }
    }
}


#[derive(Default, Debug)]
pub struct CliUnstable {
    pub print_im_a_teapot: bool,
}

impl CliUnstable {
    pub fn parse(&mut self, flags: &[String]) -> CargoResult<()> {
        if !flags.is_empty() && !nightly_features_allowed() {
            bail!("the `-Z` flag is only accepted on the nightly channel of Cargo")
        }
        for flag in flags {
            self.add(flag)?;
        }
        Ok(())
    }

    fn add(&mut self, flag: &str) -> CargoResult<()> {
        let mut parts = flag.splitn(2, '=');
        let k = parts.next().unwrap();
        let v = parts.next();

        fn parse_bool(value: Option<&str>) -> CargoResult<bool> {
            match value {
                None |
                Some("yes") => Ok(true),
                Some("no") => Ok(false),
                Some(s) => bail!("expected `no` or `yes`, found: {}", s),
            }
        }

        match k {
            "print-im-a-teapot" => self.print_im_a_teapot = parse_bool(v)?,
            _ => bail!("unknown `-Z` flag specified: {}", k),
        }

        Ok(())
    }
}

fn channel() -> String {
    env::var("__CARGO_TEST_CHANNEL_OVERRIDE_DO_NOT_USE_THIS").unwrap_or_else(|_| {
        ::version().cfg_info.map(|c| c.release_channel)
            .unwrap_or_else(|| String::from("dev"))
    })
}

fn nightly_features_allowed() -> bool {
    match &channel()[..] {
        "nightly" | "dev" => true,
        _ => false,
    }
}
