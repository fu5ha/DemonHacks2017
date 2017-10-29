import React, { Component } from 'react'
import keydown, { ALL_KEYS } from 'react-keydown'
import CodeInput from './CodeInput.js'
import CodeDisplay from './CodeDisplay.js'
import './Typer.css'

class Typer extends Component {
  constructor () {
    super()
    this.state = {
      charactersTyped: 0,
      currentLine: 0,
      firstMistake: -1,
      shouldLineReset: false,
      formattedData: ['']
    }
  }
  componentDidMount () {
    this.setState({
      ...this.state,
      formattedData: this.props.codeData.split('\n').map(line => line + '\n')
    })
  }
  componentWillReceiveProps ({codeData}) {
    this.setState({
      ...this.state,
      formattedData: codeData.split('\n').map(line => line + '\n')
    })
  }
  increment () {
    this.setState({
      ...this.state,
      lines: this.state.lines + 1
    })
  }
  codeChanged (charsTyped, firstMistake, isEnter) {
    const lineLength = this.state.formattedData[this.state.currentLine].length
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
            this.state.formattedData.slice(
              0,
              this.state.currentLine + 2
            )
          }
        </CodeDisplay>
        <CodeInput shouldLineReset={this.state.shouldLineReset} resetCallback={this.resetCallback.bind(this)} currentLine={this.state.formattedData[this.state.currentLine]} changeCallback={this.codeChanged.bind(this)} />
      </div>
    )
  }
}

export default keydown(ALL_KEYS)(Typer)
