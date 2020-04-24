import * as React from 'react'
import * as yup from 'yup'
import { Formik } from 'formik'
import { Alert, Button, Form } from 'react-bootstrap'
import {sendRequest, sendEzApiRequest, sendEZAPIForm, sendForm} from '../../common/ApiHelper'
import { schema } from 'json-schema-traverse'
import Redirect from "react-router-dom/es/Redirect";

class AddToolsForm extends React.Component {

    addToolAPIEndpoint = '/objects/add';
    TAGS_URI = '/tags';

    SUPPORTED_FORMATS = ['image/jpg', 'image/jpeg', 'image/gif', 'image/png'];
    FILE_SIZE = 1000000;

    constructor(props){
        super(props);
        this.state = {
            tags : []
        };
        this.attemptAddTool = this.attemptAddTool.bind(this);
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
    }

    attemptAddTool = (values) => {

        const tags = [];

        //Change the input format of the values -> we want [ { name : "name" } , { name : "name" }, ... ]
        for (let i = 0; i < values.toolTags.length ; i++) {
            tags.push({ "name" : values.toolTags[i]})
        }

        const data = {
            name: values.toolName,
            description: values.toolDescription,
            objectTags : tags
        }

        const formData = new FormData();
        formData.append('object', JSON.stringify(data));
        formData.append('image', values.toolImage);

        console.log(data)
        for (let p of formData.entries()){
            console.log(p)
        }
        // A voir en fonction de notre API
        sendForm(this.addToolAPIEndpoint, 'POST', formData
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
            alert("Outil ajouté!")
        ).then(
            //this.props.history.push("DashBoard")
            //return <Redirect to={'DashBoard'} />

        )
    }

    //TODO  Ilias : < !!! > ATTENTION, en JS le ``this`` est OBLIGATOIRE. Ex:  validationSchema={schema} utilise une variable schema qui est probablement undefined meme si this.schema est défini

    // validation rules
    schema = yup.object().shape({
        toolName: yup
            .string()
            .required('Requis')
            .min(3, 'Minumum 3 caractères!')
            .max(20),
        toolDescription: yup.string().min(0).max(200),
        toolTags: yup.array().of(yup.string())
            .min(1, 'Minimum 1 catégorie!'),
        /*toolImage: yup
            .mixed()
            //TODO : Les tests ne marche pas, peut être doit on faire un autre schema pour que les files ?
            //Maurice : Les tests marchent, mais le message ne s'affiche pas
            //Pb asynchrone de Formik ? https://github.com/testing-library/react-testing-library/issues/224
            .test(
                'fileSize',
                'Fichier trop grand!',
                (value) =>
                    typeof value !== 'undefined' && value.size <= this.FILE_SIZE
            )
            .test(
                'fileType',
                'Extension invalide!',
                (value) =>
                    typeof value !== 'undefined' &&
                    this.SUPPORTED_FORMATS.includes(value.type)
            ),*/
    })
    render() {
        return (
            <>
                <h1>Ajouter un outil</h1>
                <Formik
                    validationSchema={this.schema}
                    onSubmit={(values, { setSubmitting }) => {
                        this.attemptAddTool(values)
                    }}
                    initialValues={{
                        toolName: '',
                        toolDescription: '',
                        toolImage: '',
                        toolTags: [],
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

export default AddToolsForm