import React, { Component } from 'react'
import keydown, { ALL_KEYS } from 'react-keydown'
import CodeInput from './CodeInput.js'
import CodeDisplay from './CodeDisplay.js'
import './Typer.css'

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

const formattedData = data.split('\n').map(line => line + '\n')

class Typer extends Component {
  constructor () {
    super()
    this.state = {
      displayedLines: 6,
      charactersTyped: 0,
      currentLine: 0,
      firstMistake: -1,
      shouldLineReset: false
    }
  }
  increment () {
    this.setState({
      ...this.state,
      lines: this.state.lines + 1
    })
  }
  codeChanged (charsTyped, firstMistake, isEnter) {
    const lineLength = formattedData[this.state.currentLine].length
    if (charsTyped === lineLength - 1 && firstMistake === -1 && isEnter) {
      this.setState({
        ...this.state,
        shouldLineReset: true,
        currentLine: this.state.currentLine + 1,
        charactersTyped: 0
      })
    } else {
      this.setState({
        ...this.state,
        charactersTyped: charsTyped,
        firstMistake: firstMistake
      })
    }
  }
  resetCallback () {
    this.setState({
      ...this.state,
      shouldLineReset: false
    })
  }
  render () {
    return (
      <div className='App'>
        <CodeDisplay currentLineIdx={this.state.currentLine} firstMistake={this.state.firstMistake} charactersTyped={this.state.charactersTyped} className='perl'>
          {
            formattedData.slice(
              0,
              this.state.currentLine + this.state.displayedLines
            )
          }
        </CodeDisplay>
        <CodeInput shouldLineReset={this.state.shouldLineReset} resetCallback={this.resetCallback.bind(this)} currentLine={formattedData[this.state.currentLine]} changeCallback={this.codeChanged.bind(this)} />
      </div>
    )
  }
}

export default keydown(ALL_KEYS)(Typer)
