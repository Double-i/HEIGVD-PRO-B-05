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

    SEARCH_URI = '/search';

    //fetch tous les tools de la DB :
    //TODO : Géré les éventuelles erreur et comprendre comment avoir la bonne array de ce "Promise"
    //objects = sendEzApiRequest("/objects");

    constructor(props){
        super(props);
        this.state = {
            value : '',
            tools : []//TODO : fetch le json et le replacer ici
        };

        //console.log(this.objects)

        /*sendEzApiRequest("/objects")
            .then(function(response){
                //this.tools = response;
                console.log(response, allTools);
                allTools = response;
                //that.setState({tools:response});
            });*/
     /*  this.handleChange = this.handleChange.bind(this);
       this.handleSubmit = this.handleSubmit.bind(this);*/
    }

    componentDidMount() {
        sendEzApiRequest("/objects")
            .then((response) =>{
                //this.tools = response;
                this.setState({tools:response});
                console.log(this.state);
            });
    }

    handleSubmit(event){
        console.log(event);
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