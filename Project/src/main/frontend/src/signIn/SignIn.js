import React, { useState } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import { Container, Form, Button, Spinner, Modal } from 'react-bootstrap'
import { Link } from 'react-router-dom'
import { Formik } from 'formik'
import * as yup from 'yup'

function SignInForm(props) {
    const schema = yup.object({
        userEmail: yup.string().required(),
        userPassword: yup.string().required(),
    })
    const [showSigInForm, setShowSigInForm] = useState(false)
    let values = {}
    return (
        <>
            <Button onClick={() => setShowSigInForm(true)}>Se Loguer</Button>
            <Modal
                size="lg"
                show={showSigInForm}
                onHide={() => setShowSigInForm(false)}
                aria-labelledby="example-modal-sizes-title-lg"
            >
                <Modal.Header closeButton>
                    <Modal.Title id="example-modal-sizes-title-lg">
                        Connexion
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Container>
                        <Formik
                            validationSchema={schema}
                            onSubmit={console.log}
                            initialValues={{}}
                        >
                            {({
                                handleSubmit,
                                handleChange,
                                touched,
                                isValid,
                                errors,
                            }) => (
                                <Form noValidate onSubmit={handleSubmit}>
                                    <Form.Group controlId="formBasicEmail">
                                        <Form.Label>E-mail</Form.Label>
                                        <Form.Control
                                            name="userEmail"
                                            type="email"
                                            placeholder="E-mail"
                                            value={values.userEmail}
                                            onChange={handleChange}
                                            isValid={
                                                touched.userEmail &&
                                                !errors.userEmail
                                            }
                                        />
                                    </Form.Group>

                                    <Form.Group controlId="formBasicPassword">
                                        <Form.Label>Mot de passe</Form.Label>
                                        <Form.Control
                                            name="userPassword"
                                            type="password"
                                            placeholder="Mot de passe"
                                            value={values.userPassword}
                                            onChange={handleChange}
                                            isValid={
                                                touched.userPassword &&
                                                !errors.userPassword
                                            }
                                        />
                                    </Form.Group>
                                    <Form.Group controlId="formBasicCheckbox">
                                        <Link to="/login/forget">
                                            Oops j'ai oublié mon mot de passe
                                            &#128584;
                                        </Link>
                                    </Form.Group>
                                    <Button
                                        variant="primary"
                                        type="submit"
                                        size="lg"
                                        block
                                    >
                                        Se connecter
                                        <Spinner
                                            as="span"
                                            animation="border"
                                            size="sm"
                                            role="status"
                                            aria-hidden="true"
                                        />
                                    </Button>
                                </Form>
                            )}
                        </Formik>
                    </Container>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default SignInForm
