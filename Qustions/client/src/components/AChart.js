import Chart from './Chart';
import React, { Component } from 'react';
import {Bar, Line, Pie} from 'react-chartjs-2';

export default props =>{
    const {chartData,location,legendPosition}=props;
    
    return (
        <div>
        <Bar
            data={chartData}
            options={{
            title:{
                display:true,
                text:'Largest Cities In '+location,
                fontSize:25
            },
            legend:{
                display:true,
                position:legendPosition
            }
        }}
        />

        </div>
    );
}

