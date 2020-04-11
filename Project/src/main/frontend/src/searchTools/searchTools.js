import DisplayTools from "./displayTools";
import React from "react";
import {Formik} from "formik";
import {sendEzApiRequest} from "../common/ApiHelper";


let allTools = [
    {
        name : "perceuse",
        description : "super perceuse",
        ownerUserName : "Michel",
        image : [],
        objecttags : [
            {
                name: "bricolage",
                id: 2
            },
            {
                name: "electrique",
                id: 3
            },
        ],
        id : 2
    },
    {
        name : "marteau",
        description : "il est marteau",
        ownerUserName : "Jean",
        image : [],
        objecttags : [
            {
                name: "bricolage",
                id: 2
            },
            {
                name: "manuel",
                id: 4
            },
        ],
        id : 3
    }
];

class SearchTools extends React.Component{

    SEARCH_URI = '/search'

    constructor(props){
        super(props);
        this.state = {
            value : '',
            tools : allTools
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({value: event.target.value});
    }

    handleSubmit(event){

        sendEzApiRequest(this.SEARCH_URI, 'POST', {
            searchString: this.state.value
        }).then(
            result => {
                if (result.status === 403) {
                    console.log('No tools founded')
                } else {
                    console.log('items founded')
                    this.state.tools = result.data;
                }
            },
            error => {
                console.log('Connection PAS ok', error)
            }
        )
    }

    render(){
        return (
            <div className="container">

                <form onSubmit={this.handleSubmit}>
                    {/* TODO : Ajouter d'autre champ de recherche -> Tags, ville, etc*/}
                    <input type="text" className="input" onChange={this.handleChange} placeholder="Search..." />
                    <input type="submit" value="Rechercher"/>
                </form>

                <DisplayTools
                    data = {this.state.tools}
                />
            </div>
        )
    }
}

export default SearchTools;