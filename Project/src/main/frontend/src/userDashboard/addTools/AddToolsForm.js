import * as React from 'react'
import * as yup from 'yup'
import regex from '../../common/regex'
import {Formik} from 'formik'
import {Button, Form} from "react-bootstrap"

function AddToolsForm(props) {
    //TODO : Move la validation d'une image ailleurs?
    const SUPPORTED_FORMATS = [
        'image/jpg',
        'image/jpeg',
        'image/gif',
        'image/png',
    ]
    const FILE_SIZE = 10000
    const validateImageType = (value) => {
        if (value) {
            let type = value.match(/[^:]\w+\/[\w-+\d.]+(?=;|,)/)[0]
            return SUPPORTED_FORMATS.includes(type)
        }
    }

    const attemptAddTool = (values) => {
        //TODO
        alert(values)
    }


    // Ilias: la validation pour l'image ne semble pas correcte il doit avoir une petite erreur ...à voir

    // validation rules
    const schema = yup.object({
        toolName: yup.string().required('Requis'),
        toolDescription: yup.string().min(0).max(200),
        toolImage: yup
            .array()
            .nullable()
            .required('Validation requise')
            .test(
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
            ),
    })

    return (
        <>
            <Formik
                validationSchema={schema}
                onSubmit={(values, {setSubmitting}) => {
                    attemptAddTool(values)
                    alert('yooy')
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
