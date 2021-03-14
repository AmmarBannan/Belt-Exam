import {useEffect, useState} from "react";
import { Link,navigate,Router} from "@reach/router"
import Chart from '../components/Chart';
import {Doughnut,Pie,Line,Bar} from 'react-chartjs-2';
import axios from "axios"

export default props => {
    const [polls,setPolls]=useState([])
    const [loaded,setloaded]=useState(true)

    useEffect(() => {
        axios.get("http://localhost:8000/api/votings")
            .then(response => setPolls(response.data))
            .catch(error => console.log("There was an issue: ", error))
        },[loaded])
    return(
        <div >
            <button style={{backgroundColor:"cyan",float:"right",marginRight:15}} onClick={()=>navigate("/polls/new")}>Create your own Poll</button><br/>
            
            <div style={{display:"Flex",alignContent:"center"}}>
                <div style={{display:"Block",backgroundColor:"silver",margin:15,width:750,border:"solid",height:390,padding:20}}>
                    <h2>Top 3 Polls</h2><br/>
                    {
                    {polls}==null?
                    <p>dsd</p>
                    :
                    polls.length > 0 && polls.map((poll, index)=>{
                        return<div style={{width:300,height:70,backgroundColor:"white",margin:10,padding:15,border:"solid"}}>
                        <Link to={"/poll/"+poll._id}>{poll.question}</Link> <Link to={"/poll/withoutchart/"+poll._id} style={{backgroundColor:"yellow",textDecoration:0,color:"red"}}>without chart</Link><br/>
                        <div style={{display:"inline"}}>
                        {!poll.option1?
                        <></>
                        :< >{poll.option1}/{poll.op1}   </>}
                        {!poll.option2?
                        <></>
                        :<>{poll.option2}/{poll.op2}    </>}
                        {!poll.option3?
                        <></>
                        :<>{poll.option3}/{poll.op3}    </>}
                        {!poll.option4?
                        <></>
                        :<>{poll.option4}/{poll.op4}</>}
                        </div>
                        <a3 ></a3>
                        {/* <Link to="/poll/${poll._id}"><Link> */}
                        </div>
                    })
                    }
                </div>
                <div style={{display:"Block",backgroundColor:"silver",margin:15,width:700,border:"solid",float:"right",marginright:-10,padding:20}}>
                    <h2>Recent polls</h2><br/>
                    {{polls}==null?
                    <p>dsd</p>
                    :
                    polls.length > 0 && polls.map((poll, index)=>{
                        return<div style={{width:300,height:70,backgroundColor:"white",margin:10,padding:15,border:"solid"}}>
                        <Link to={"/poll/"+poll._id}>{poll.question}</Link>
                        <p>{poll.option1} , {poll.option2} , {poll.option3} , {poll.option4} , {poll.option5}</p>
                        <a3 ></a3>
                        {/* <Link to="/poll/${poll._id}"><Link> */}
                        </div>
                    })
                    }
                </div>
            </div>
        </div>
    )

}