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
  socket.emit('createNewGame')
}

function playerJoinGame (gameId) {
  socket.emit('playerJoinGame', {gameId: gameId})
}

function onPlayerFailedToJoinGame (cb) {
  socket.on('playerFailedToJoinGame', data => cb(data.message))
}

function startCountdown (id) {
  socket.emit('startCountdown', {gameId: id})
}

function onCountdownReceived (cb) {
  socket.on('count', data => cb(data.count))
}

function onGameStarted (cb) {
  socket.on('gameStarted', () => cb())
}

export default {
  onConnected,
  onPlayerJoinedRoom,
  onNewGameCreated,
  onReceiveNewPlayerState,
  createNewGame,
  playerJoinGame,
  onPlayerFailedToJoinGame,
  startCountdown,
  onCountdownReceived,
  onGameStarted
}
