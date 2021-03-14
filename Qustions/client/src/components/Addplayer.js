import React, { useState } from "react"
import axios from "axios"
import { navigate, Link } from "@reach/router"
import Header2 from "../views/Header2"
export  default  props => {

    const [name, setName] = useState("")
    const [position, setposition] = useState("")
    const [errors, setErrors] = useState([])

    const onSubmit = e => {
        e.preventDefault()
        axios.post("http://localhost:8000/api/createplayer", {name: name,position:position,status1:"Not Playing",status2:"Not Playing",status3:"Not Playing"})
            .then(() => navigate("/"))
            .catch(err =>{
                const errorResponse = err.response.data.errors; // Get the errors from err.response.data
                const errorArr = []; // Define a temp error array to push the messages in
                for (const key of Object.keys(errorResponse)) { // Loop through all errors and get the messages
                    errorArr.push(errorResponse[key].message)
                }
                // Set Errors
                setErrors(errorArr);
            })
    }
        return(
            <div><Header2/>
                <div>
                    <div >
                    {errors.map((err, index) => <small key={index} style={{color:"red"}}>{err}</small>)}
                        <p>Add a new Player:</p>
                    </div>
                </div>
                <form onSubmit={ onSubmit }>
                    <div >
                        <label>Name:</label>
                        <input onChange={(e)=>setName(e.target.value)} type="text"/>
                        <input onChange={(e)=>setposition(e.target.value)} type="text" />
                    </div>
                    <div >
                        <button onClick={()=>navigate("/player/list")} type="button" className="btn btn-secondary btn-sm">Cancel</button>
                        <button className="btn btn-primary btn-sm">ADD</button>
                    </div>
                </form>
            </div>
        )
    }