import DisplayTools from "../toolsUtil/displayTools";
import React from "react";
import {SessionContext} from "../common/SessionHelper";
import {sendEzApiRequest,  sendRequestSimple} from "../common/ApiHelper";
import {Form, Button, Container, Row, Col} from "react-bootstrap";
import { Map, GoogleApiWrapper, Marker, InfoWindow} from 'google-maps-react';
import MapContainer  from "./map";

const containerStyle = {
    position: 'relative',

    width: '100%',
    height: '100%'
}

export class SearchTools extends React.Component{
    SEARCH_URI = '/objects'
    TAGS_URI = '/tags'

    static contextType = SessionContext

    constructor(props){
        super(props);

        this.state = {
            search: '',
            tools: [],
            tags: [],
            activeMarker: {},
            searchTags: [],
            nbTools: 0,
            pages: [],
            currentPage: null,
            currentURLFilter: "",
            selectedTool: {},
            showingInfoWindow: false,
        }

        sendEzApiRequest(this.SEARCH_URI)
            .then((response) =>{
                this.setState({tools:response});
            })
            .catch(err => alert(err));

        //Utilisé pour appeler this dans handleSubmit
        this.handleSubmit = this.handleSubmit.bind(this)
        this.handleTagChange = this.handleTagChange.bind(this)
    }

    //Au chargement, on affiche tout les tools
    componentDidMount() {

        //all the objects
        sendRequestSimple('/objects/count').then(
            (result) =>
            {
                let pages = []
                let nbPages = result/10;
                for(let i = 0; i < nbPages; i++)
                {
                    pages.push(
                        <li className="page-item" key={`page-li-${i}`} onClick={() => {this.loadPage(i)}}>
                        <a className = "page-link"  key={`page-link-${i}`} >
                            {i + 1}
                        </a>
                        </li>
                    )
                }

                this.setState({nbTools:result,pages:pages})

            }
        )

        sendEzApiRequest(this.SEARCH_URI)
            .then((response) =>{
                this.setState({tools:response})
            }, error => {
                console.log(error)
            })


        sendEzApiRequest(this.TAGS_URI)
            .then( (response) => {
                    //Get tags from db
                    if(response.status === 403) {
                        console.log('pas reussi a fetch les tags...');
                    }else{
                        this.setState({tags: response.map((value) => value.name)})
                    }
                }, error => {
                    console.log(error)
                })
    }

    loadPage(page)
    {
        let URL = this.SEARCH_URI
        let parameters ='?';
        parameters+='page='+page;

        //Search by name
        if(this.state.search !== ''){
            parameters += '&names='+ this.state.search;
            if(this.state.searchTags.length !== 0)
                parameters += '&';
        }

        if(this.state.searchTags.length !== 0){
            if(this.state.search === '')
                URL += "/filter"
            parameters += '&tags=';
            for(let i = 0; i < this.state.searchTags.length; ++i){
                if(i > 0)
                    parameters += ",";
                parameters += this.state.searchTags[i];
            }
        }

        sendEzApiRequest(URL+parameters)
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
    //En cas de submit, on recherche la query dans
    handleSubmit(event){

        //Si le champ est vide, on affiche tout les objects
        let URL = this.SEARCH_URI;

        let path = '';
        let parameters = ''
        //Search by name
        if(this.state.search !== ''){
            parameters += '?names=' + this.state.search;
            if(this.state.searchTags.length !== 0)
                parameters += '&';
        }

        if(this.state.searchTags.length !== 0){
            if(this.state.search === '')
                parameters += ""
            parameters += '?tags=';
            for(let i = 0; i < this.state.searchTags.length; ++i){
                if(i > 0)
                    parameters += ",";
                parameters += this.state.searchTags[i];
            }
        }
        if(parameters === '')
            path = '/count'
        else
            path = '/filter/count'
        sendRequestSimple(this.SEARCH_URI+path+parameters).then(
            (result) =>
            {
                console.log(result)
                let pages = []
                let nbPages = result/10;
                for(let i = 0; i < nbPages; i++)
                {
                    pages.push(
                        <li className="page-item" key={`page-li-${i}`} onClick={() => {this.loadPage(i)}}>
                            <a className = "page-link"  key={`page-link-${i}`} >
                                {i + 1}
                            </a>
                        </li>
                    )
                }

                this.setState({nbTools:result,pages:pages})
                console.log(this.state.pages)
            }
        )

        event.preventDefault();

        if(parameters === '')
            path = ''
        else
            path = '/filter'
        sendEzApiRequest(this.SEARCH_URI+path+parameters)
            .then(
                (result) => {
                    if (result.status === 403) {
                        console.log('No tools founded')
                    } else {
                        console.log('items founded')

                        this.setState({tools : result})
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
        return this.state.tools.map((tool, idx) => {
            return <Marker onClick = {this.onMarkerClick}
                           key ={`search-tool-maps-marker-${idx}-${tool.name}`}
                           name = {tool.name}
                           position={{
                               lat: tool.owner.address.lat,
                               lng: tool.owner.address.lng
                           }}/>
        })
    }

    render(){
        return (
            <>
                <Row>
                    <Col>
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
                    </Col>
                    <Col>
                        <MapContainer tools = {this.state.tools}/>
                    </Col>
                </Row>
                <Row>
                    <DisplayTools
                        data = {this.state.tools}
                        hideOwner={false}
                        hideBorrowButton={!this.context.session.isUserLogin()}
                        hideEditButton={true}
                        hideDeleteButton={true}
                    />
                </Row>
                <Row className={"justify-content-md-center"}>
                    <nav className={"paginationTools"} >
                        <ul className={"pagination"}>
                            {this.state.pages}
                        </ul>
                    </nav>
                </Row>

            </>
    )
    }
}

export default GoogleApiWrapper({
    apiKey: 'AIzaSyCwmIS0mKIfWPvmZke7plXkeR1uZ6ahwcU'
})(SearchTools);