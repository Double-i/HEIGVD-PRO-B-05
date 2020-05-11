import DisplayTools from "./displayTools";
import React, {useState} from "react";
import {sendEzApiRequest} from "../common/ApiHelper";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { Map, GoogleApiWrapper, Marker, InfoWindow} from 'google-maps-react';
import Row from "react-bootstrap/Row";

const mapStyles = {
    width:  '80%',
    height: '100%'
}

//TODO : Si le user est pas log, on arrive pas a fetch les objets ?!
class SearchTools extends React.Component{
    SEARCH_URI = '/objects'
    TAGS_URI = '/tags'
    constructor(props){
        super(props);

        this.state = {
            search : '',
            tools : [],
            tags : [],
            searchTags : [],
            showingInfoWindow: false,
            activeMarker: {},
            selectedTool: {}
        }

        sendEzApiRequest(this.SEARCH_URI)
            .then((response) =>{
                console.log(response);
                this.setState({tools:response});
                console.log(this.state.tools);
            })
            .catch(err => alert(err));

        //Utilisé pour appeler this dans handleSubmit
        this.handleSubmit = this.handleSubmit.bind(this)
        this.handleTagChange = this.handleTagChange.bind(this)
    }

    //Au chargement, on affiche tout les tools
    componentDidMount() {
        sendEzApiRequest(this.SEARCH_URI)
            .then((response) =>{
                this.setState({tools:response})
            })


        sendEzApiRequest(this.TAGS_URI)
            .then( (response) => {
                    //Get tags from db
                    if(response.status === 403) {
                        console.log('pas reussi a fetch les tags...');
                    }else{
                        this.setState({tags: response.map((value) => value.name)})
                    }
                })
    }

    //En cas de submit, on recherche la query dans
    handleSubmit(event){
        //Si le champ est vide, on affiche tout les objects
        let URL = this.SEARCH_URI;

        //Search by name
        if(this.state.search !== ''){
            URL += '/filter?names=' + this.state.search;
            if(this.state.searchTags.length !== 0)
                URL += '&';
        }

        if(this.state.searchTags.length !== 0){
            if(this.state.search === '')
                URL += "/filter?"
            URL += 'tags=';
            for(let i = 0; i < this.state.searchTags.length; ++i){
                if(i > 0)
                    URL += ",";
                URL += this.state.searchTags[i];
            }
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
    }

    //Dynaminc update of searched tags fields
    handleTagChange(e){
        let options = e.target.options;
        let value = [];
        for (let i = 0, l = options.length; i < l; i++) {
            if (options[i].selected) {
                value.push(options[i].value);
            }
        }
        this.setState({searchTags: value});
    }

    onMarkerClick = (props, marker, e) => {
        console.log("Clicked on marker !");
        this.setState({
            selectedTool: this.state.tools.find(tool => tool.name ===props.name),
            activeMarker: marker,
            showingInfoWindow: true
        });
    }

    onMapClicked = (props) => {
        if (this.state.showingInfoWindow) {
            this.setState({
                showingInfoWindow: false,
                activeMarker: null
            })
        }
    };

    getInfoWindow()
    {
        return <InfoWindow
            marker = {this.state.activeMarker}
            visible = {this.state.showingInfoWindow}>
            <div>
                <h1>{this.state.selectedTool === undefined ? "" : this.state.selectedTool.name}</h1>
                <p>
                    {this.state.selectedTool === undefined ? "" : this.state.selectedTool.description}
                </p>
            </div>
        </InfoWindow>;
    }

    getMarkers()
    {
        return this.state.tools.map(tool => {
            console.log('getting tool : '+tool.name+' at '+tool.owner.address);
            return <Marker onClick = {this.onMarkerClick}
                           key = {tool.name}
                           name = {tool.name}
                           position={{
                               lat: tool.owner.address.lat,
                               lng: tool.owner.address.lng
                           }}/>
        })
    }

    render(){
        return (
            <div className="container" style={{
                marginTop: '10px',
            }}>
                <Form onSubmit={this.handleSubmit}>
                    <Form.Group controlId="formBasicEmail" >
                        <Form.Control
                            type="text"
                            placeholder="Rechercher"
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
                <div style={{marginBottom: '80%', marginTop: '5%'}}>
                    <Map
                        key={0}
                        google={this.props.google}
                        zoom={10}
                        style={mapStyles}
                        initialCenter={{ lat: 46.5, lng: 6.5}}
                        onClick={this.onMapClicked}
                    >
                        {this.getMarkers()}
                        {this.getInfoWindow()}
                    </Map>
                </div>
                <div>
                    <DisplayTools
                          data = {this.state.tools}
                    />
                </div>

            </div>
        )
    }
}

export default GoogleApiWrapper({
    apiKey: 'AIzaSyCwmIS0mKIfWPvmZke7plXkeR1uZ6ahwcU'
})(SearchTools);