import React from 'react'
import Typer from './Typer'

import './App.css'

const data = `# loads object
sub load
{
  my $flds = $c->db_load($id,@_) || do {
    Carp::carp "Can't load (class: $c, id: $id): '$!'"; return undef
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
POD till the end of file`

class App extends React.Component {
  render () {
    return (
      <div>
        <div className='typer-left'>
          <Typer codeData={data}/>
        </div>
        <div className='typer-right'>
          <Typer codeData={data}/>
        </div>
      </div>
    )
  }
}

export default App
