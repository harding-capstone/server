open module com.shepherdjerred.capstone.server {
  requires static lombok;
  requires com.shepherdjerred.capstone.logic;
  requires com.shepherdjerred.capstone.common;
  requires com.shepherdjerred.capstone.network;
  requires com.google.common;
  requires io.netty.all;
  requires org.apache.logging.log4j;
  exports com.shepherdjerred.capstone.server.network.connection.local;
  exports com.shepherdjerred.capstone.server.network.packet;
  exports com.shepherdjerred.capstone.server.server;
}
