import React from 'react'
import Typer from './Typer'
import api from './api'

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
  constructor (props) {
    super(props)
    this.state = {
      gameStage: 'home',
      gameId: -1
    }
  }
  createGameClicked () {
    this.setState({
      ...this.state,
      gameStage: 'creating'
    })
  }
  gameCreated (id) {
    this.setState({
      gameStage: 'host',
      gameId: id
    })
  }
  joinGameClicked () {

  }
  render () {
    if (this.state.gameStage === 'home') {
      console.log("home")
      return (
        <div className ='App' >
          <h1 className = 'homeName' >Demon Typer</h1>
          <button className = 'btnCre' onClick={this.createGameClicked.bind(this)}>Create Game</button>
          <button className = 'btnJoin' onClick={this.joinGameClicked.bind(this)}>Join Game</button>
        </div>
      )
    } else if (this.state.gameStage === 'host') {
      return (
        <div className ='App'>        
          <h3 className >Game ID:</h3>
          {this.state.gameId}
          <button onClick={this.startGameClicked.bind(this)}>Start Game</button>
        </div>
      )
    } else if (this.state.gameStage === 'creating') {
      return (
        <div className = 'App'>
          <h3 className = 'cGame'>Creating game, please wait</h3>
        </div>
      )
    } else if (this.state.gameStage === 'join') {
      return (
        <div className ='App'>        
          <h3>Input Game ID:</h3>
          <form onSubmit={this.joinGameClicked.bind(this)}>
            <input />
            <button>Join Game</button>
          </form>
        </div>
      )
    } else if (this.state.gameStage === 'joining') {
      return (
        <h3>Joining game, please wait</h3>
      )
    } else if (this.state.gameStage === 'lobby') {
      return (
        <h3>Game {this.state.gameId} joined, waiting for host.</h3>
      )
    } else if (this.state.gameStage === 'main') {
      return (
        <div>
          <div className='typer-left'>
            <Typer codeData={data} />
          </div>
          <div className='typer-right'>
            <Typer codeData={data} />
          </div>
          <code className='timestamp'>
            {this.state.timestamp.toString()}
          </code>
        </div>
      )
    }
  }
}

export default App
