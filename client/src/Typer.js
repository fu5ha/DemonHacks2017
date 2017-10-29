import React, { Component } from 'react'
import keydown, { ALL_KEYS } from 'react-keydown'
import CodeInput from './CodeInput.js'
import CodeDisplay from './CodeDisplay.js'
import './Typer.css'

class Typer extends Component {
  constructor (props) {
    super(props)
    this.state = {
      isRemote: props.isRemote,
      charactersTyped: 0,
      currentLine: 0,
      firstMistake: -1,
      shouldLineReset: false,
      formattedData: props.codeData.split('\n').map(line => line + '\n'),
      score: 0
    }
  }
  componentWillReceiveProps ({codeData}) {
    this.setState({
      ...this.state,
      formattedData: codeData.split('\n').map(line => line + '\n')
    })
    if (this.state.isRemote) {
      this.setState({
        ...this.state,
        ...this.props.remoteState
      })
    }
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
        charactersTyped: 0,
        score: this.state.score + this.state.charactersTyped + 1
      })
    } else {
      this.setState({
        ...this.state,
        charactersTyped: charsTyped,
        firstMistake: firstMistake
      })
    }
    if (!this.state.isRemote) {
      const currState = {
        charactersTyped: this.state.charactersTyped,
        currentLine: this.state.currentLine,
        firstMistake: this.state.firstMistake,
        shouldLineReset: this.state.shouldLineReset,
        score: this.state.score + this.state.charactersTyped
      }
      this.props.onStateChange(currState)
    }
  }
  resetCallback () {
    this.setState({
      ...this.state,
      shouldLineReset: false
    })
  }
  render () {
    if (this.state.isRemote) {
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
        </div>
      )
    } else {
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
}

export default keydown(ALL_KEYS)(Typer)
