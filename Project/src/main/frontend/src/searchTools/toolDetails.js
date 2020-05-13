import {sendEzApiRequest} from "../common/ApiHelper"
import React from "react"
import ImageGallery from 'react-image-gallery'
import Gallery from 'react-grid-gallery';
import {Container, Row} from "react-bootstrap";

class ToolDetails extends React.Component{

    SEARCH_URI = '/objects'

    state = {
        description: "",
        name: "",
        images: [],
        objectTags: [],
        owner: {}
    }



    constructor(props)
    {
        super(props);

    }

    async componentDidMount() {

        sendEzApiRequest(this.SEARCH_URI + '/' + this.props.match.params.id)
            .then((response) => {
                console.log(response);
                this.setState(response);
                console.log(response)
                response.images.map( test => (
                  console.log(test)
                ))

                let temp = []
                    response.images.map(image => (
                        temp.push(
                        {
                            src : "http://localhost:8080/api/image/"+image.pathToImage.toString(),
                            thumbnail :"http://localhost:8080/api/image/"+image.pathToImage.toString(),
                            thumbnailWidth: "10%",
                            thumbnailHeight: "10%"
                        })
                ));
                console.log(temp)

                this.setState({images : temp})
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