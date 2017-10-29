/**
 * Module dependencies.
 */

var app = require('./app')
var socket = require('./sockets')
var debug = require('debug')('demonhacks2017:server')
var http = require('http')
var socketio = require('socket.io')

/**
 * Get port from environment and store in Express.
 */

var port = normalizePort(process.env.PORT || '3000')
app.set('port', port)

/**
 * Create HTTP server.
 */

var server = http.createServer(app)

/**
 * Listen on provided port, on all network interfaces.
 */

server.listen(port)
server.on('error', onError)
server.on('listening', onListening)

/**
 * Normalize a port into a number, string, or false.
 */

function normalizePort(val) {
  var port = parseInt(val, 10)

  if (isNaN(port)) {
    // named pipe
    return val
  }

  if (port >= 0) {
    // port number
    return port
  }

  return false
}

/**
 * Event listener for HTTP server "error" event.
 */

function onError(error) {
  if (error.syscall !== 'listen') {
    throw error
  }

  var bind = typeof port === 'string'
    ? 'Pipe ' + port
    : 'Port ' + port

  // handle specific listen errors with friendly messages
  switch (error.code) {
    case 'EACCES':
      console.error(bind + ' requires elevated privileges')
      process.exit(1)
      break
    case 'EADDRINUSE':
      console.error(bind + ' is already in use')
      process.exit(1)
      break
    default:
      throw error
  }
}

/**
 * Event listener for HTTP server "listening" event.
 */

function onListening() {
  var addr = server.address()
  var bind = typeof addr === 'string'
    ? 'pipe ' + addr
    : 'port ' + addr.port
  debug('Listening on ' + bind)
}

var io = socketio(server)

function PlayerManager () {
    this.player_clients = []
    this.player_ids = []
}
PlayerManager.prototype.registerPlayer = function (client, id) {
    if (this.player_clients.length < 2) {
        this.player_clients.push(client);
        this.player_ids.push(id);
    }
    if (this.player_clients.length == 2) {
        setInterval(() => this.player_clients[0].emit(
            ''
        ))
    }
}

var manager = new PlayerManager()

io.on('connection', (client) => {
  client.on('subscribeToOther', (id) => {
    console.log('client is subscribing to updates with id ', id);
    manager.registerPlayer(client, id);
  });
});
