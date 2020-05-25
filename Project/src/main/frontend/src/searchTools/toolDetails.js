import {sendEzApiRequest} from "../common/ApiHelper"
import React from "react"
import ImageGallery from 'react-image-gallery'
import Gallery from 'react-grid-gallery';
import {Container, Row, Col, ListGroup, Card, Carousel} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import BorrowPanel from "./BorrowPanel";
import ReportPanel from "./reportPanel";

class ToolDetails extends React.Component{

    SEARCH_URI = '/objects'

    constructor(props)
    {
        super(props);
        this.state = {
            description: "",
            name: "",
            images:
            [{
                src : undefined,
                thumbnail: undefined,
                thumbnailWidth: undefined,
                thumbnailHeight:undefined,
            }],

            objectTags: [],
            owner: {},
            toolId:"",
            borrowModalShow: false,
            reportModalShow: false
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
                        objectTags : response.objectTags,
                        toolId:response.id
                    })
            })
            .catch(err => alert(err));
    }

    /**
     * Display the borrow pannel
     * @param value
     */
    setBorrowModalShow(value) {
        this.setState({borrowModalShow: value});
    }
    setReportModalShow(value)
    {
        this.setState({reportModalShow:value})
    }
    render()
    {
        return (
        <Container class="align-content-center">
            <div className="detailsTitle" style={{
                backgroundImage: `url(${this.state.images[0].src})`,
                height: '400px'
            }}>
                <div className="tagsDetails">
                {
                    this.state.objectTags.map(tag =>(
                        <Button class="btn-light">{tag.name}</Button>
                    ))
                }
                </div>
            </div>
             <Card style={
                        {
                            width:'100%',
                            margin: '0 auto',
                            float: 'none',
                            marginBottom: '10px'
                        }}>
                    <Card.Body>
                        <Card.Title>
                            {this.state.name}
                        </Card.Title>
                        <Card.Text>
                            Propriétaire : {this.state.owner.userName}
                        </Card.Text>

                        <Card.Subtitle>
                            Description
                        </Card.Subtitle>
                        <Card.Text>
                            {this.state.description}
                        </Card.Text>



                    </Card.Body>

                        {/*Modals*/}
                        <ReportPanel
                            show={this.state.reportModalShow}
                            onHide={() => this.setReportModalShow(false)}
                            toolId={this.state.toolId}
                        />
                        <BorrowPanel
                            show={this.state.borrowModalShow}
                            onHide={() => this.setBorrowModalShow(false)}
                            tool={{
                                    name:this.state.name,
                                    id:this.state.toolId
                                }}


                        />
                        <Card.Footer>
                            <Button
                                disabled={false} //TODO : avoir une props de l'item isBorrowable !
                                key={"buttonId" + this.props.id}
                                style={{
                                    marginBottom: '10px',
                                    marginRight: '10px'
                                }}
                                onClick={() => this.setBorrowModalShow(true)}
                            >
                                Emprunter
                            </Button>


                            <Button
                                disabled={false} //TODO : avoir une props de l'item isBorrowable !
                                key={"buttonId" + this.props.id}
                                style={{
                                    marginBottom: '10px'
                                }}
                                onClick={() => this.setReportModalShow(true)}
                            >
                                Signaler
                            </Button>
                        </Card.Footer>
                </Card>
            <Gallery  images={this.state.images}/>

            </Container>
        )
    }
}

export default ToolDetails