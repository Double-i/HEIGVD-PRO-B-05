import * as React from 'react'
import * as yup from 'yup'
import regex from '../../common/regex'
import {Formik} from 'formik'
import {Alert, Button, Form} from "react-bootstrap"
import {sendRequest} from "../../common/ApiHelper";
import {schema} from "json-schema-traverse";

class AddToolsForm extends React.Component{

    //TODO : Get tag from Database!
    tags = [
        'jardin',
        'bois',
        'éléctronique'
    ];

    // TODO delete comment - Pour Bastien, le /api est ajouté automatiquement, change signUpAPIEndpoint avec le bon endpoint
    addToolAPIEndpoint = '/tools';

    //TODO : Move la validation d'une image ailleurs?
    SUPPORTED_FORMATS = [
        'image/jpg',
        'image/jpeg',
        'image/gif',
        'image/png',
    ];
    FILE_SIZE = 1000000;


    attemptAddTool = (values) => {

        sendRequest(this.addToolAPIEndpoint,{
          data : JSON.stringify(values) //TODO : problème avec le fetch encore , a regarder commrnt on fait
        }).then(
                result => {
                    console.log("Ajout de l'outil ok!")
                }
            )
    };


    // Ilias: la validation pour l'image ne semble pas correcte il doit avoir une petite erreur ...à voir

    // validation rules
    schema = yup.object({
        toolName: yup.string().required('Requis').min(0).max(20),
        toolDescription: yup.string().min(0).max(200),
        toolTags: yup.array().of(yup.string()).min(1),
        toolImage: yup
            .mixed()
            //TODO : Les tests ne marche pas, peut être doit on faire un autre schema pour que les files ?
/*            .test(
                'fileSize',
                'File is too large',
                (value) =>
                     typeof value !== 'undefined' && value.size <= FILE_SIZE
            )
           .test(
                'fileType',
                'Your Error Message',
                (value) =>
                    typeof value !== 'undefined' &&
                    SUPPORTED_FORMATS.includes(value.type)
            )*/
    });
    render() {

    return (
        <>
            <h1>Ajouter un outil</h1>
            <Formik
                validationSchema={schema}
                onSubmit={(values, {setSubmitting}) => {
                    this.attemptAddTool(values);
                }}
                initialValues={{
                    toolName: '',
                    toolDescription: '',
                    toolImage: '',
                    toolTags: '',
                }}
            >
                {({values, handleSubmit, handleChange, tags}) => (

                    <Form noValidate onSubmit={handleSubmit}>
                        <Form.Group controlId="formToolName">
                            {JSON.stringify(values)}
                            {JSON.stringify(tags)}
                            <Form.Control
                                name="toolName"
                                type="text"
                                placeholder="Nom de l'outil"
                                value={values.toolName}
                                onChange={handleChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="formToolDescription">
                            <Form.Control
                                name="toolDescription"
                                type="text"
                                placeholder="Description de l'outil"
                                onChange={handleChange}
                                value={values.toolDescription}
                            />
                        </Form.Group>
                        <Form.Group controlId={"formToolTags"}>
                            Tags (ctrl + click pour choisir plusieurs tags)
                            <select
                                multiple="multiple"
                                className="form-control"
                                name="toolTags"
                                value={values.toolTags}
                                onChange={handleChange}
                                style={{ display: 'block' }}
                            >
                                {(() => {
                                    const options = [];
                                    for (let i = 0; i < this.tags.length; i++) {
                                        options.push(<option value={this.tags[i]}>{this.tags[i]}</option>);
                                    }
                                    return options;
                                })()}

                            </select>
                        </Form.Group>
                        <Form.Group controlId="formToolFile">
                            <Form.Control
                                name="toolImage"
                                type="file"
                                placeholder="Photo de l'outil"
                                onChange={handleChange}
                                value={values.toolImage}
                            />
                        </Form.Group>
                        <Button variant="primary" type="submit" size="lg" block>
                            Valider
                        </Button>
                    </Form>
                )}
            </Formik>
        </>
    )}
}

export default AddToolsForm
