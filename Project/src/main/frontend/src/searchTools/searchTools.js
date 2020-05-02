import DisplayTools from "../toolsUtil/displayTools";
import React, {useState} from "react";
import {sendEzApiRequest, sendRequest, sendRequestSimple} from "../common/ApiHelper";
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
            pages : [],
            currentPage : null
        };

        //Utilisé pour appelé this dans handleSubmit
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
                    console.log("page "+i)
                    pages.push(
                        <span onClick={() => {this.loadPage(i)}}>{i}</span>
                    )
                }

                this.setState({nbTools:result,pages:pages})

            }
        )

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

    loadPage(page)
    {
        let URL = this.SEARCH_URI

        URL+='?page='+page;

        //Search by name
        if(this.state.search !== ''){
            URL += '&' + this.state.search;
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

        event.preventDefault();
        sendEzApiRequest(URL)
            .then(
                (result) => {
                    console.log(result)
                    if (result.status === 403) {
                        console.log('No tools founded')
                    } else {
                        console.log('items founded')
                        let pages = []
                        console.log(result)
                        let nbPages = result/10;
                        for(let i = 0; i < nbPages; i++)
                        {
                            console.log("page "+i)
                            pages.push(
                                <span onClick={() => {this.loadPage(i)}}>{i}</span>
                            )
                        }

                        this.setState({tools : result,nbTools:result.length,pages:pages})
                    }
                },
                error => {
                    console.log('Connection PAS ok', error)
                })

        //Pour éviter de "vraiment" appuyer sur le submit et refresh la page

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