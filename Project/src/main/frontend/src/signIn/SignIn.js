import React, {useState} from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import {Alert, Button, Container, Form, Modal, Spinner} from 'react-bootstrap'
import {Formik} from 'formik'
import * as yup from 'yup'
import {sendEzApiRequest} from '../common/ApiHelper'

const LOGIN_URI = '/authenticate'

/**
 * Component containing the signin form. It's displayed in a modal.
 * @param props : - setLoggedUser() function used to send the logged in user information to the parent component (App.js)
 *                - setShowSignInForm() function used to show or not the form
 *                - showSignInForm prop is the var used to know if it should display the modal. This is the var changed
 *                when we call setShowSignInForm() function
 *
 * @returns {React.Component}
 */
function SignInForm(props) {

    // This state is used to know if the application is trying to signin
    const [isLogging, setIsLogging] = useState(false)

    // this 3 states are used to display error message after the reponse of the request for login has been received
    const [hasConnectionProblem, setHasConnectionProblem] = useState(false)
    const [hasBeenLoggedIn, setHasBeenLoggedIn] = useState(false)
    const [hasWrongCredential, sethasWrongCredential] = useState(false)

    /**
     * This function is triggered when the user clicked on the login button
     * @param username given by the user
     * @param password given by the user
     */
    const attemptLogin = (username, password) => {

        // Reset error message, To avoid displaying previous error/success message
        setHasConnectionProblem(false)
        sethasWrongCredential(false)
        setHasBeenLoggedIn(false)

        // Send a request
        sendEzApiRequest(LOGIN_URI, 'POST', {
            userName: username,
            password: password,
        }).then(
            result => {

                setIsLogging(false)
                props.setLoggedUser({
                    tokenDuration: result.tokenDuration,
                    username: result.user.userName,
                    admin: result.user.admin,
                    lastname: result.user.lastName,
                    firstname: result.user.firstName,
                    address: result.user.address
                })
            },
            error => {
                setIsLogging(false)

                if(error.errorCode === 401){
                    sethasWrongCredential(true)
                }else{
                    console.log('Error occurs', error)
                    setHasConnectionProblem(true)

                }
            }
        )
    }

    // validation rules
    const schema = yup.object({
        userName: yup
            .string()
            .required('Requis')
            .min(3)
            .max(50),
        userPassword: yup.string().required('Requis'),
    })

    return (
        <>
            <Modal
                size="lg"
                show={props.showSignInForm}
                onHide={() => {
                    // hide modal and displayed messages
                    props.setShowSignInForm(false)
                    setHasConnectionProblem(false)
                    sethasWrongCredential(false)
                    //setHasBeenLoggedIn(false)
                }}
                aria-labelledby="example-modal-sizes-title-lg"
            >
                <Modal.Header closeButton>
                    <Modal.Title id="example-modal-sizes-title-lg">
                        Connexion
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Alert variant="success" hidden={!hasBeenLoggedIn}>
                        Vous êtes connecté !
                    </Alert>
                    <Alert variant="danger" hidden={!hasConnectionProblem}>
                        hum humm.. problème de connexion au serveur
                    </Alert>
                    <Alert variant="warning" hidden={!hasWrongCredential}>
                        Mauvais nom d'utilisateur ou/et mot de passe
                    </Alert>
                    <Container>
                        <Formik
                            validationSchema={schema}
                            onSubmit={(values, { setSubmitting }) => {
                                setIsLogging(!isLogging)
                                attemptLogin(
                                    values.userName,
                                    values.userPassword
                                )
                            }}
                            initialValues={{ userName: '', userPassword: '' }}
                        >
                            {({
                                handleSubmit,
                                handleChange,
                                touched,
                                isValid,
                                values,
                                errors,
                            }) => (
                                <Form noValidate onSubmit={handleSubmit}>
                                    <Form.Group controlId="formBasicEmail">
                                        <Form.Label>
                                            Nom d'utilisateur
                                        </Form.Label>
                                        <Form.Control
                                            name="userName"
                                            type="text"
                                            placeholder="Nom d'utilisateur"
                                            value={values.userName}
                                            onChange={handleChange}
                                            isValid={
                                                touched.userName &&
                                                !errors.userName
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
                                    <Button
                                        variant="primary"
                                        type="submit"
                                        size="lg"
                                        block
                                        disabled={isLogging}
                                    >
                                        Se Connecter
                                        <Spinner
                                            as="span"
                                            animation="border"
                                            size="sm"
                                            role="status"
                                            aria-hidden="true"
                                            hidden={!isLogging}
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
