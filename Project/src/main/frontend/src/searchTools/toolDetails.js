import {sendEzApiRequest} from "../common/ApiHelper"
import React from "react"
import ImageGallery from 'react-image-gallery'

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
                this.images = this.state.images.map(image => (
                    {
                        thumbnail : image,
                        original : image
                    }
                ));
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
            <div>
                <ImageGallery items = {this.state.images}/>
            </div>
            <p>Propriétaire : {this.state.owner.userName}</p>
        </div>
    }
}

export default ToolDetails