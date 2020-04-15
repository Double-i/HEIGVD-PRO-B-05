import DisplayTools from "./displayTools";
import React, {useState} from "react";
import {sendEzApiRequest} from "../common/ApiHelper";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";


//TODO : Si le user est pas log, on arrive pas a fetch les objets ?!
class SearchTools extends React.Component{

    SEARCH_URI = '/objects';

    constructor(props){
        super(props);

        this.state = {
            search : '',
            tools : [],
            tags : [],
            searchTags : []
        };
        //Utilisé pour appelé this dans handleSubmit
        this.handleSubmit = this.handleSubmit.bind(this)
        this.handleTagChange = this.handleTagChange.bind(this)
    }

    //Au chargement, on affiche tout les tools
    componentDidMount() {
        sendEzApiRequest(this.SEARCH_URI)
            .then((response) =>{
                this.setState({tools:response})})

        //Get tags from db
        this.setState({tags:['sport' , 'skateboard' , 'jardin', 'ete']})
    }

    //En cas de submit, on recherche la query dans
    handleSubmit(event){
        //Si le champ est vide, on affiche tout les objects
        let URL = this.SEARCH_URI;

        //Search by name
        if(this.state.search !== ''){
            URL += '/find/name/' + this.state.search;
        }
        //Pour éviter de "vraiment" appuyer sur le submit et refresh la page
        event.preventDefault();
        sendEzApiRequest(URL)
            .then(
                (result) => {
                    if (result.status === 403) {
                        console.log('No tools founded')
                    } else {
                        console.log('items founded')
                        this.setState({tools : result});
                    }
                },
                error => {
                    console.log('Connection PAS ok', error)
                })
        //Adding search by tags

        //
    }

    handleTagChange(e){
        let options = e.target.options;
        let value = [];
        for (let i = 0, l = options.length; i < l; i++) {
            if (options[i].selected) {
                value.push(options[i].value);
            }
        }
        this.setState({searchTag: value});
    }

    render(){
        return (
            <div className="container">

                <Form onSubmit={this.handleSubmit}>
                    <Form.Group controlId="formBasicEmail" >
                        <Form.Control
                            type="text"
                            placeholder="Search"
                            onChange={event => this.setState({search : event.target.value})}
                        />
                    </Form.Group>

                    <Form.Group controlId={'formToolTags'}>
                        Tags (ctrl + click pour choisir plusieurs tags)
                        <select
                            multiple="multiple"
                            className="form-control"
                            name="toolTags"
                            onChange={this.handleTagChange}
                            style={{ display: 'block' }}
                        >
                            {this.state.tags.map((value, idx) => (
                                <option
                                    key={`tag-${idx}`}
                                    value={value}
                                >
                                    {value}
                                </option>
                            ))}
                        </select>
                    </Form.Group>

                    <Button variant="primary" type="submit">
                        Rechercher
                    </Button>
                </Form>

                <DisplayTools
                    data = {this.state.tools}
                />
            </div>
        )
    }
}

export default SearchTools;