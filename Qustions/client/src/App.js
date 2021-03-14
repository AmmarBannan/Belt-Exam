import React, { useState, useEffect } from 'react';
import io from 'socket.io-client';
import Main from "./Main"
import Games from "./views/Games"
import { Link,navigate,Router } from "@reach/router"
import Addplayer from "./components/Addplayer"
import PlayerTabel from "./components/PlayerTabel"
import Home from "./components/Home"
import New from "./components/New"
import DChart from "./components/DChart"
import Polls from "./components/Polls"
import Pollsnormal from "./components/Pollsnormal"




function App() {
  return(
    <div >
    <div style={{backgroundColor:"silver",margin:10,border:"solid",textAlign:"center"}}><h1>Voting Dojo </h1></div>
      <Router>
        <New path="/polls/new"/>
        <Home path="/"/>
        <Polls path="/poll/:id"/>
        <Pollsnormal path="/poll/withoutchart/:id"/>
        {/* <DChart path="/poll:id"/> */}
      </Router>
    </div>
  );
}

export default App;