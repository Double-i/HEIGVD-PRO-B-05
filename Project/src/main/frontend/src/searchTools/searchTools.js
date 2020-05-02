import DisplayTools from "../toolsUtil/displayTools";
import React, {useState} from "react";
import {sendEzApiRequest, sendRequest, sendSimpleRequest} from "../common/ApiHelper";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";


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
            nbTools : 0,
            currentURLFilter : "",
            pages : []
        };

        //Utilisé pour appelé this dans handleSubmit
        this.handleSubmit = this.handleSubmit.bind(this)
        this.handleTagChange = this.handleTagChange.bind(this)
    }

    //Au chargement, on affiche tout les tools
    componentDidMount() {
        sendSimpleRequest(this.SEARCH_URI+"/count")
            .then(
                (count) => {
                    console.log(count)
                    sendEzApiRequest(this.SEARCH_URI)
                        .then((result) => {
                            this.setState({tools : result, nbTools:count});
                            this.handlePage();
                        })
                },
                (error) => {
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
        console.log("nbobject " )

        event.preventDefault();
        sendSimpleRequest(this.SEARCH_URI+"/count")
            .then(
                (count) => {
                    console.log("nbobject " + count)
                    sendEzApiRequest(URL)
                        .then(
                            (result) => {
                                if (result.status === 403) {
                                    console.log('No tools founded')
                                } else {
                                    console.log('items founded')
                                    this.setState({tools : result, currentURLFilter:URL, nbTools:count});
                                }
                            },
                            error => {
                                console.log('Connection PAS ok', error)
                            })
                }
            )

        //Pour éviter de "vraiment" appuyer sur le submit et refresh la page

    }
    loadPage(i)
    {
        sendEzApiRequest(this.state.currentURLFilter+"&page="+i)
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
    handlePage()
    {
        let pages = [];
        for(let i = 0; i < this.state.nbTools; i++)
        {
            pages.push(
                <span onClick={this.loadPage(i)}>{i}</span>
            )
        }
        this.setState({pages:pages})
        console.log(pages)
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

    render(){
        return (
            <div className="container" style={{
                marginTop: '10px'
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

                <DisplayTools
                    data = {this.state.tools}
                    hideOwner={false}
                    hideBorrowButton={false}
                    hideEditButton={true}
                />
                {this.state.pages}
            </div>
        )
    }
}

export default SearchTools;