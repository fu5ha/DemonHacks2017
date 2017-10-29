var io
var gameSocket
exports.initGame = function (sio, socket) {
  io = sio
  gameSocket = socket
  gameSocket.emit('connected', { message: 'You are connected!' })
  gameSocket.on('createNewGame', createNewGame)
  gameSocket.on('playerJoinGame', playerJoinGame)
  gameSocket.on('startCountdown', startCountdown)
  gameSocket.on('playerStateChanged', playerStateChanged)
}

function createNewGame () {
  var id = (Math.random() * 100000) | 0
  this.emit('newGameCreated', {gameId: id, socketId: this.id})
  this.join(id.toString())
}

function playerJoinGame (data) {
  var playerSocket = this

  if (io.sockets.adapter.rooms[data.gameId] !== undefined) {
    data.socketId = playerSocket.id
    playerSocket.join(data.gameId)
    io.sockets.in(data.gameId).emit('playerJoinedRoom', data)
  } else {
    playerSocket.emit('playerFailedToJoinGame', {message: 'This room does not exist'})
  }
}

function startCountdown (data) {
  var count = 5
  io.sockets.in(data.gameId).emit('count', {count: count})
  var interval = setInterval(function () {
    if (count > 0) {
      count--
      io.sockets.in(data.gameId).emit('count', {count: count})
    } else {
      io.sockets.in(data.gameId).emit('gameStarted')
      clearInterval(interval)
    }
  }, 1000)
}

function playerStateChanged (data) {
  io.sockets.in(data.gameId).emit('playerStateChanged', data)
}
