import React from 'react'
import Typer from './Typer'

import './App.css'

class App extends React.Component {
  render () {
    return (
      <div>
        <div className='typer-left'>
          <Typer />
        </div>
        <div className='typer-right'>
          <Typer />
        </div>
      </div>
    )
  }
}

export default App
