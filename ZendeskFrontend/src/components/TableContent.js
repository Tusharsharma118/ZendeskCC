import axios from "axios";
import { React, useState, useEffect } from "react";
import Table from 'react-bootstrap/Table';
import { Button, ButtonGroup, Card, Container} from "react-bootstrap";
import './TableContent.css'

const TableContent = () => {
    // set the variables to store values using useState Hook
    const [tickets, setTickets] = useState([]);
    const [hasNext, setHasNext] = useState(true);
    const [prev, setPrev] = useState("");
    const [next, setNext] = useState("");
    const [rowRecord, setRowRecord] = useState({});

    // setup the onClick show the pop-up with ticket details
    const [modal, setModal] = useState(false)

    const showRowDetails = (value) =>  {
       setModal(!modal)
       setRowRecord(value)
    }

    // fetch records from the backend
    const getRecords = async (value) => {
        try {
            const nextCheck = (value === 'prev' ) ? false : true
            // condition to check if it is an initial request
            if (prev === ''){
                const record = await axios.get("http://localhost:8080/tickets/",
                {
                    params:{
                        "nextURI" : "",
                        "prevURI" :"",
                        "next" : nextCheck,
                        "pageSize": 25,
                        "hasNext": true
                    }
                    
                })
                setTickets(record.data.tickets);
                setHasNext(record.data.meta.has_more);
                setPrev(record.data.links.prev);
                setNext(record.data.links.next);
            } else {  
                    const record = await axios.get("http://localhost:8080/tickets/",
                    {
                        params:{
                            "nextURI" : next,
                            "prevURI" :prev,
                            "next" : nextCheck,
                            "pageSize": 25,
                            "hasNext": hasNext
                        }
                        
                    })
                    setTickets(record.data.tickets);
                    setHasNext(record.data.meta.has_more);
                    setPrev(record.data.links.prev);
                    setNext(record.data.links.next);        
            }
        } catch (error) {
            console.log(error);
        }
    };

    // on load this hook will be called and it will fetch the records.
    useEffect(() => {
        getRecords();
    }, []);
    // console.log(tickets[0]?.id)
    return (
        <>
            {/* condition to show the pop-up */}
            {
                modal && (
                    <div className="modalBackground">
                        <div className="modalContainer">
                            <Card className="modal-text">
                                <Card.Body>
                                    <Card.Title>{rowRecord?.id}: {rowRecord?.subject}</Card.Title>
                                    <Card.Subtitle className="mb-2 text-muted">Status: {rowRecord?.status}</Card.Subtitle>
                                    <Card.Text>{rowRecord?.description}</Card.Text>
                                    <Card.Text>{rowRecord?.tags?.map(tag => ( <p> {tag} </p>   ))}</Card.Text>
                                    <Button 
                                        onClick={() => showRowDetails({})}
                                        variant='danger'>
                                        Close
                                    </Button>
                                </Card.Body>
                            </Card>
                        </div>
                    </div>
                )
            }
            <Container>
                <Table striped bordered hover size="sm">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Subject</th>
                            {/* <th>Description</th> */}
                            <th>Created Date</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    {
                        tickets?.map(record => (
                            <tbody key={record.id}>
                                <tr onClick={() => showRowDetails(record)}>
                                    <td>{record?.id}</td>
                                    <td>{record?.subject}</td>
                                    {/* <td>{record?.description}</td> */}
                                    <td>{record?.created_at}</td>
                                    <td>{record?.status}</td>
                                </tr>
                            </tbody>
                        ))
                    }
                </Table>
            </Container>
            <ButtonGroup className="btn-group-modal mb-2">
                <Button 
                    className="btn-modal"
                    variant='primary'
                    onClick={() => getRecords('prev')}
                    disabled = {tickets[0]?.id > 1  ? false : true}
                >Previous</Button>
                <Button
                    className="btn-modal"
                    variant='primary'
                    onClick={() => getRecords('next')}
                    disabled = {hasNext ? false : true}
                >Next</Button>
            </ButtonGroup>
        </>
    )
}

export default TableContent;