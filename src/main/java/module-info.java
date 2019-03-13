module com.shepherdjerred.capstone.server {
  requires static lombok;

  requires com.shepherdjerred.capstone.logic;
  requires com.shepherdjerred.capstone.common;
  requires com.shepherdjerred.capstone.events;
  requires com.shepherdjerred.capstone.network;
  requires com.google.common;
  requires io.netty.all;
  requires org.apache.logging.log4j;
  requires gson;
  requires gson.extras;
}
