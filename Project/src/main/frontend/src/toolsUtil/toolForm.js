import * as React from "react";
import {Formik} from "formik";
import {Button, Form} from "react-bootstrap";
import {sendEzApiRequest, sendForm} from "../common/ApiHelper";
import * as yup from "yup";


class ToolForm extends React.Component {

    TAGS_URI = '/tags';

    constructor(props) {
        super(props);

        if(this.props.action === 'add'){
            this.state = {
                toolId: '',
                toolName: '',
                toolDescription: '',
                toolTags: '',
                toolImage: '',
                tags: [],
                ApiRequest : '/objects/add'
            };
        }else if(this.props.action === 'update'){
            this.state = {
                toolId: this.props.tool.id,
                toolName: this.props.tool.name,
                toolDescription: this.props.tool.description,
                toolTags: this.props.tool.objectTags,
                toolImage: this.props.tool.toolImage,
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
        formData.append('image', values.toolImage);

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

        console.log("mounted !")
        console.log(this.props.tool)
        console.log(this.state)

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
                                        setFieldValue(
                                            'toolImage',
                                            event.currentTarget.files[0]
                                        )
                                    }}
                                />
                            </Form.Group>
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