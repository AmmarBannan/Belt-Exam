import {useEffect, useState} from "react";
import { Link,navigate,Router} from "@reach/router"
import Chart from '../components/Chart';
import {Doughnut,Pie,Line,Bar} from 'react-chartjs-2';

export default props => {
    const [vote,setVote]=useState()
    const data = {
        labels: [
            'Red',
            'Blue',
            'Yellow'
        ],
        datasets: [{
            data: [300, 50, 100],
            backgroundColor: [
            '#FF6384',
            '#36A2EB',
            '#FFCE56'
            ],
            hoverBackgroundColor: [
            '#FF6384',
            '#36A2EB',
            '#FFCE56'
            ]
        }]
    };
    return(
        <div>
            <button style={{backgroundColor:"cyan",float:"right",marginRight:15}} onClick={()=>navigate("/")}>Back to Home</button><br/>
            <div style={{backgroundColor:"silver",border:"solid",margin:10,hieght:700}}>
                <h1>Which snack is the Best</h1>
                <div style={{display:"flex"}}>
                    <div style={{width:500,float:"left"}}>
                        <Pie data={data} />
                    </div>
                    <tbody>
                        <tr><td><h3>Cheez-its</h3></td><td></td></tr>
                        <tr><td><h3>Goldfish</h3></td><td></td></tr>
                    </tbody>
                </div>
            </div>
        </div>
    )

}