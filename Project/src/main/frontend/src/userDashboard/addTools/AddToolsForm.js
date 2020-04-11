import * as React from 'react'
import * as yup from 'yup'
import { Formik } from 'formik'
import { Alert, Button, Form } from 'react-bootstrap'
import { sendRequest, sendEzApiRequest } from '../../common/ApiHelper'
import { schema } from 'json-schema-traverse'

class AddToolsForm extends React.Component {
    //TODO : Get tag from Database!
    tags = ['jardin', 'bois', 'éléctronique']

    addToolAPIEndpoint = '/tools'

    //TODO : Move la validation d'une image ailleurs?
    SUPPORTED_FORMATS = ['image/jpg', 'image/jpeg', 'image/gif', 'image/png']
    FILE_SIZE = 1000000

    attemptAddTool = (values) => {
        console.log('attemptAddTool', values)
        const data = {
            name: values.toolName,
        }

        // A voir en fonction de notre API
        sendEzApiRequest('/tools', 'POST', data).then(
            (result) => {
                console.log(result)
            },
            (errors) => {
                console.log(errors)
            }
        )
    }

    //TODO  Ilias : < !!! > ATTENTION, en JS le ``this`` est OBLIGATOIRE. Ex:  validationSchema={schema} utilise une variable schema qui est probablement undefined meme si this.schema est défini

    // validation rules
    schema = yup.object({
        toolName: yup
            .string()
            .required('Requis')
            .min(3, 'UNE MESSAGE ICI AUTREMENT ANGLAIS')
            .max(20),
        toolDescription: yup.string().min(0).max(200),
        toolTags: yup.array().of(yup.string()).min(1),
        toolImage: yup
            .mixed()
            //TODO : Les tests ne marche pas, peut être doit on faire un autre schema pour que les files ?
            .test(
                'fileSize',
                'File is too large',
                (value) =>
                    typeof value !== 'undefined' && value.size <= this.FILE_SIZE
            )
            .test(
                'fileType',
                'Your Error Message',
                (value) =>
                    typeof value !== 'undefined' &&
                    this.SUPPORTED_FORMATS.includes(value.type)
            ),
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
                                {JSON.stringify(values)}
                                {JSON.stringify(tags)}
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
                                    style={{ display: 'block' }}
                                >
                                    {this.tags.map((value, idx) => (
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
