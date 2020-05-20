import {sendEzApiRequest} from "../common/ApiHelper"
import React from "react"
import ImageGallery from 'react-image-gallery'
import Gallery from 'react-grid-gallery';
import {Container, Row} from "react-bootstrap";

class ToolDetails extends React.Component{

    SEARCH_URI = '/objects'


    constructor(props)
    {
        super(props);
        this.state = {
            description: "",
            name: "",
            images: [],
            objectTags: [],
            owner: {}
        }
    }

    componentDidMount() {
        sendEzApiRequest(this.SEARCH_URI + '/' + this.props.match.params.id)
            .then((response) => {

                let temp = []
                response.images.map(image => (
                    temp.push(
                    {
                        src : "http://127.0.0.1:8080/api/image/"+image.pathToImage.toString(),
                        thumbnail :"http://127.0.0.1:8080/api/image/"+image.pathToImage.toString(),
                        thumbnailWidth: "10%",
                        thumbnailHeight: "10%"
                    })
                ));

                this.setState(
                    {images : temp,
                        owner : response.owner,
                        name : response.name,
                        description : response.description,
                        objectTags : response.objectTags
                    })
            })
            .catch(err => alert(err));
    }

    render()
    {
        return <div>
            <h1>{this.state.name}</h1>
            <p>{this.state.description}</p>
            <h2>
                Tags
            </h2>
            <ul>
            {
                this.state.objectTags.map(tag =>(
                    <li key={tag.id}>{tag.name}</li>
                ))
            }
            </ul>

            <Container>
                <Row>
                     <p>Propriétaire : {this.state.owner.userName}</p>
                </Row>
            </Container>
            <Gallery images={this.state.images}/>


        </div>
    }
}

export default ToolDetails