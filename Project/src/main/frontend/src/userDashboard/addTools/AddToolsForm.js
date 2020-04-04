import * as React from 'react'
import * as yup from 'yup'
import regex from '../../common/regex'
import {Formik} from 'formik'
import {Alert, Button, Form} from "react-bootstrap"
import {sendRequest} from "../../common/ApiHelper";

function AddToolsForm(props) {


    // TODO delete comment - Pour Bastien, le /api est ajouté automatiquement, change signUpAPIEndpoint avec le bon endpoint
    const addToolAPIEndpoint = '/tools'

    //TODO : Move la validation d'une image ailleurs?
    const SUPPORTED_FORMATS = [
        'image/jpg',
        'image/jpeg',
        'image/gif',
        'image/png',
    ];
    const FILE_SIZE = 1000000;


    const attemptAddTool = (values) => {
        sendRequest(addToolAPIEndpoint,{
          data : JSON.stringify(values) //TODO : problème avec le fetch encore , a regarder commrnt on fait
        }).then(
                result => {
                    console.log("Ajout de l'outil ok!")
                }
            )
    };


    // Ilias: la validation pour l'image ne semble pas correcte il doit avoir une petite erreur ...à voir

    // validation rules
    const schema = yup.object({
        toolName: yup.string().required('Requis').min(0).max(20),
        toolDescription: yup.string().min(0).max(200),
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

    return (
        <>
            <h1>Ajouter un outil</h1>
            <Formik
                validationSchema={schema}
                onSubmit={(values, {setSubmitting}) => {
                    attemptAddTool(values);
                }}
                initialValues={{
                    toolName: '',
                    toolDescription: '',
                    toolImage: '',
                }}
            >
                {({values, handleSubmit, handleChange}) => (
                    <Form noValidate onSubmit={handleSubmit}>
                        <Form.Group controlId="fromToolName">
                            <Form.Control
                                name="toolName"
                                type="text"
                                placeholder="Nom de l'outil"
                                value={values.toolName}
                                onChange={handleChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="fromToolDescription">
                            <Form.Control
                                name="toolDescription"
                                type="text"
                                placeholder="Description de l'outil"
                                onChange={handleChange}
                                value={values.toolDescription}
                            />
                        </Form.Group>
                        <Form.Group controlId="fromToolFile">
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
    )
}

export default AddToolsForm
