import openSocket from 'socket.io-client'
const socket = openSocket('http://localhost:3000')

// socket.on('newGameCreated', onNewGameCreated)
// socket.on('playerJoinedRoom', onPlayerJoinedRoom)
// socket.on('beginNewGame', onBeginNewGame)
// socket.on('newPlayerData', onNewPlayerData)
// socket.on('gameOver', onGameOver)

function onConnected (cb) {
  socket.on('connected', () => cb(socket.socket.sessionid))
}

function onPlayerJoinedRoom (cb) {
  socket.on('playerJoinedRoom', data => cb(data))
}

function onNewGameCreated (cb) {
  socket.on('newGameCreated', data => cb(data))
}

function onReceiveNewPlayerState (cb) {
  socket.on('newPlayerState', data => cb(data))
}

function createNewGame () {
  socket.emit('createNewGame');
}