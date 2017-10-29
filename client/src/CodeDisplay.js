import hljs from 'highlight.js'
import React from 'react'

import './CodeDisplay.css'
import './atelier-sulphurpool-dark.css'

class CodeDisplay extends React.Component {
  constructor () {
    super()
    this.state = {
      shouldHighlight: false,
      cachedChildren: ''
    }
  }
  componentDidMount () {
    this.highlightCode()
    this.setState({
      ...this.state,
      cachedChildren: this.props.children.toString()
    })
  }

  componentWillReceiveProps ({children, shouldLineReset}) {
    if (children.toString() !== this.state.cachedChildren) {
      this.setState({
        shouldHighlight: true,
        cachedChildren: children.toString()
      })
    } else {
      this.setState({
        shouldHighlight: false
      })
    }
  }

  componentDidUpdate () {
    if (this.state.shouldHighlight) {
      this.highlightCode()
      this.setState({
        shouldHighlight: false
      })
    }
  }

  highlightCode () {
    const nodes = this.el.querySelectorAll('pre code')

    for (let i = 0; i < nodes.length; i++) {
      hljs.highlightBlock(nodes[i])
    }
  }

  setEl (el) {
    this.el = el
  }

  render () {
    const {children, className} = this.props

    return (
      <div>
        <pre ref={this.setEl.bind(this)}><code className={className}>{children}</code></pre>
        <pre><code className='ghost-code'>
          {
            children.map((line, idx) => {
              let ghostPart = ''
              let colorPart = ''
              let wrongPart = ''
              if (idx < this.props.currentLineIdx) {
                colorPart = line
              } else if (idx === this.props.currentLineIdx) {
                ghostPart = line.slice(this.props.charactersTyped, line.length)
                if (this.props.firstMistake !== -1) {
                  wrongPart = line.slice(this.props.firstMistake, this.props.charactersTyped)
                  colorPart = line.slice(0, this.props.firstMistake)
                } else {
                  colorPart = line.slice(0, this.props.charactersTyped)
                }
              } else {
                ghostPart = line
              }
              return (
                <span key={idx}>
                  <span className='ghost-invis'>{colorPart}</span>
                  <span className='ghost-wrong'>{wrongPart}</span>
                  {ghostPart}
                </span>
              )
            })
          }
        </code></pre>
      </div>
    )
  }
}

CodeDisplay.defaultProps = {
  className: null
}

export default CodeDisplay
