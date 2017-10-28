import React, { Component } from 'react'
import Highlight from 'react-highlight'
import keydown, { ALL_KEYS } from 'react-keydown'
import './App.css'
import './atelier-sulphurpool-dark.css'

let data = `
# loads object
sub load
{
  my $flds = $c->db_load($id,@_) || do {
    Carp::carp "Can\'t load (class: $c, id: $id): '$!'"; return undef
  };
  my $o = $c->_perl_new();
  $id12 = $id / 24 / 3600;
  $o->{'ID'} = $id12 + 123;
  #$o->{'SHCUT'} = $flds->{'SHCUT'};
  my $p = $o->props;
  my $vt;
  $string =~ m/^sought_text$/;
  $items = split //, 'abc';
  $string //= "bar";
  for my $key (keys %$p)
  {
    if(\${$vt.'::property'}) {
      $o->{$key . '_real'} = $flds->{$key};
      tie $o->{$key}, 'CMSBuilder::Property', $o, $key;
    }
  }
  $o->save if delete $o->{'_save_after_load'};

  # GH-117
  my $g = glob("/usr/bin/*");

  return $o;
}

__DATA__
@@ layouts/default.html.ep
<!DOCTYPE html>
<html>
  <head><title><%= title %></title></head>
  <body><%= content %></body>
</html>
__END__

=head1 NAME
POD till the end of file
`

class App extends Component {
  constructor () {
    super()
    this.state = {
      chars: 0
    }
  }
  increment () {
    this.setState({
      ...this.state,
      chars: this.state.chars + 1
    })
  }
  componentWillReceiveProps ({ keydown }) {
    if (keydown.event) {
      this.increment()
      setTimeout(this.increment.bind(this), 20)
      setTimeout(this.increment.bind(this), 40)
      setTimeout(this.increment.bind(this), 60)
    }
  }
  render () {
    return (
      <div className='App'>
        <Highlight className='perl'>
          {data.slice(0, this.state.chars)}
        </Highlight>
      </div>
    )
  }
}

export default keydown(ALL_KEYS)(App)
