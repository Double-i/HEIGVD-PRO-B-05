import * as React from "react";
import {Formik} from "formik";
import {Button, Form} from "react-bootstrap";
import {sendEzApiRequest, sendForm} from "../common/ApiHelper";
import * as yup from "yup";
import {Col,Row,Container} from "react-bootstrap";


class ToolForm extends React.Component {

    TAGS_URI = '/tags';

    constructor(props) {
        super(props);

        if(this.props.action === 'add'){
            this.state = {
                action :'add',
                toolId: '',
                toolName: '',
                toolDescription: '',
                toolTags: '',
                toolImage:'',
                displayImages : [],
                tags: [],
                ApiRequest : '/objects/add'
            };
        }else if(this.props.action === 'update'){

            this.state = {
                action :'update',
                toolId: this.props.tool.id,
                toolName: this.props.tool.name,
                toolDescription: this.props.tool.description,
                toolTags: this.props.tool.objectTags,
                displayImages : [],
                toolImage:'',
                tags: [],
                ApiRequest : '/objects/update'
            };
        }
        this.sendToolForm = this.sendToolForm.bind(this);
    }

    sendToolForm(values){
        const tags = [];

        //Change the input format of the values -> we want [ { name : "name" } , { name : "name" }, ... ]
        for (let i = 0; i < values.toolTags.length ; i++) {
            tags.push({ "name" : values.toolTags[i]})
        }

        let data = {
            name: values.toolName,
            description: values.toolDescription,
            objectTags : tags,
            id : this.state.toolId
        }


        const formData = new FormData();
        formData.append('object', JSON.stringify(data));
        for (let i = 0; i < values.toolImage.length; i++) {
            formData.append(`image`, values.toolImage[i])
        }

        for (let p of formData.entries()){
            console.log(p)
        }
        // A voir en fonction de notre API
        sendForm(this.state.ApiRequest, 'POST', formData
        ).then(
            (result) => {
                console.log("result  :")
                console.log(result)
            },
            (errors) => {
                console.log("errors  :")
                console.log(errors)
            }
        ).then(
            alert("Opération réussie!")
        ).then(
            //this.props.history.push("DashBoard")
            //return <Redirect to={'DashBoard'} />
        )
    }

    componentDidMount() {

        sendEzApiRequest(this.TAGS_URI)
            .then( (response) => {
                //Get tags from db
                if(response.status === 403) {
                    console.log('pas reussi a fetch les tags...');
                }else{
                    this.setState({tags: response.map((value) => value.name)})
                }
            })

        if(this.state.action==="update")
        {
            let images = []
            for(let i  = 0; i < this.props.tool.images.length; i++)
            {
                console.log(i)
                let temp = this.props.tool.images[i];
                images.push(
                    {
                        id:temp.id,
                        filename:temp.pathToImage.toString(),
                        url:"http://127.0.0.1:8080/api/image/"+temp.pathToImage.toString(),
                        state:"updateAble"
                    }
                )
                this.setState({displayImages:images})
            }
        }
    }

    schema = yup.object().shape({
        toolName: yup
            .string()
            .required('Requis')
            .min(3, 'Minumum 3 caractères!')
            .max(20),
        toolDescription: yup.string().min(0).max(200),
        toolTags: yup.array().of(yup.string())
            .min(1, 'Minimum 1 catégorie!'),
    })
    addNewImage(id,url,filename,state)
    {
        let images = this.state.displayImages;
        images.push({id,url,filename,state});
        this.setState({displayImages:images})
    }
    deleteImage(id)
    {
        let images = this.state.displayImages;
        images.splice(id,1)
        this.setState({displayImages:images})
    }
    displayGrid(source)
    {
        let images = this.state.displayImages
        console.log(images)
        return (
            <Container>
                <Row>
                    {
                        images.length > 0 ? (
                                images.map((image,key) =>
                                    <div>
                                        <img src={ image.url}  style={{height:"100px",width:"100px"}}/>
                                        <img styles=
                                                 {
                                                     {
                                                         position:"absolute",
                                                         right: 5,
                                                         top: 5,
                                                     }
                                                 }
                                             src="https://img.icons8.com/office/16/000000/close-window.png"
                                             onClick={() => {
                                                this.deleteImage(key);
                                             }}
                                        />
                                    </div>
                                )
                            ):
                            (
                                console.log("no images")
                            )
                    }
                </Row>
            </Container>
        )
    }
    render() {
        return(
            <>
                <h1>{this.props.formTitle}</h1>
                <Formik
                    validationSchema={this.schema}
                    onSubmit={(values, { setSubmitting }) => {
                        this.sendToolForm(values);
                    }}
                    initialValues={{
                        toolName: this.state.toolName,
                        toolDescription: this.state.toolDescription,
                        toolImage: this.state.toolImage,
                        toolTags: this.state.toolTags,
                    }}
                >
                    {({
                          values,
                          handleSubmit,
                          handleChange,
                          handleBlur,
                          tags,
                          touched,
                          isValid,
                          isInvalid,
                          errors,
                          setFieldValue,
                      }) => (
                        <Form noValidate onSubmit={handleSubmit}>
                            {/* ------------------------------------------------------------------------------------- */}
                            {/* TODO: à reproduire sur les autres champs () */}
                            {/* https://react-bootstrap.netlify.com/components/forms/#forms-validation-libraries */}

                            <Form.Group controlId="formToolName">
                                <Form.Control
                                    name="toolName"
                                    type="text"
                                    placeholder="Nom de l'outil"
                                    value={values.toolName}
                                    onChange={handleChange}

                                    onBlur={handleBlur} // TODO OBLIGATOIRE POUR UTILISER TOUCHED https://stackoverflow.com/questions/57385931/why-isnt-the-formik-touched-property-being-populated
                                    isInvalid={
                                        touched.toolName && !!errors.toolName
                                    }
                                    isValid={
                                        touched.toolName && !errors.toolName
                                    }
                                />

                                {/* TODO  <!> les feedback DOIVENT être EN DESSOUS du <FORM.CONTROL>  */}
                                <Form.Control.Feedback type="invalid">
                                    {errors.toolName}
                                </Form.Control.Feedback>
                                <Form.Control.Feedback>
                                    OK !
                                </Form.Control.Feedback>

                            </Form.Group>
                            {/* ############################################################################################ */}

                            <Form.Group controlId="formToolDescription">
                                <Form.Control
                                    name="toolDescription"
                                    type="text"
                                    placeholder="Description de l'outil"
                                    onChange={handleChange}
                                    value={values.toolDescription}
                                    onBlur={handleBlur}
                                    isInvalid={
                                        touched.toolDescription && !!errors.toolDescription
                                    }
                                    isValid={
                                        touched.toolDescription && !errors.toolDescription
                                    }
                                />
                            </Form.Group>
                            <Form.Group controlId={'formToolTags'}>
                                Tags (ctrl + click pour choisir plusieurs tags)
                                <select
                                    multiple="multiple"
                                    className="form-control"
                                    name="toolTags"
                                    value={values.toolTags}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                    isInvalid={
                                        touched.toolTags && !!errors.toolTags
                                    }
                                    isValid={
                                        touched.toolTags && !errors.toolTags
                                    }
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
                            <Form.Group controlId="formToolFile">
                                <Form.Control
                                    name="toolImage"
                                    type="file"
                                    placeholder="Photo de l'outil"
                                    onChange={(event) => {
                                        let paths = [];
                                        let files =[];
                                        for( let i = 0; i < event.target.files.length; i++)
                                        {
                                            paths.push(URL.createObjectURL(event.target.files[i]));
                                            this.addNewImage(null,URL.createObjectURL(event.target.files[i]),event.target.files[i],"new")

                                        }
                                        setFieldValue(
                                            'toolImage',
                                            event.currentTarget.files
                                        )
                                    }}
                                    multiple
                                />
                            </Form.Group>

                            {
                                this.displayGrid()
                            }

                            <Button
                                variant="primary"
                                type="submit"
                                size="lg"
                                block
                            >
                                Valider
                            </Button>
                        </Form>
                    )}
                </Formik>

            </>
        )
    }
}

export default ToolForm;