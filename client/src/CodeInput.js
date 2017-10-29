import React from 'react'

class CodeInput extends React.Component {
  componentWillReceiveProps ({shouldLineReset}) {
    if (shouldLineReset) {
      this.el.value = ''
      this.props.resetCallback()
    }
  }
  handleChange (e) {
    const charactersTyped = e.target.value.length
    let firstMistake = -1
    for (let i = 0; i < charactersTyped; i++) {
      if (e.target.value[i] !== this.props.currentLine[i]) {
        firstMistake = i
        break
      }
    }
    this.props.changeCallback(charactersTyped, firstMistake, e.key === 'Enter')
  }
  onKeyDown (e) {
    if (e.key === 'Backspace' || e.key === 'Enter') {
      this.handleChange(e)
    }
  }
  onKeyUp (e) {
    if (e.key !== 'Enter') {
      this.handleChange(e)
    }
  }
  setEl (el) {
    this.el = el
  }
  render () {
    return (
      <input ref={this.setEl.bind(this)} onKeyDown={this.onKeyDown.bind(this)} onKeyUp={this.onKeyUp.bind(this)} />
    )
  }
}

export default CodeInput
