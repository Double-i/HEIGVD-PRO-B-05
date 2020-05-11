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
            })
            .catch(err => alert(err));
    }

    render()
    {
        return <div>
            <h1>Nom : {this.state.name}</h1>
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
        </div>
    }
}

export default ToolDetails