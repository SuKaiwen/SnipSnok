import React, { useRef, useState, useEffect } from "react";
import { ListGroup, ListGroupItem, Spinner } from "react-bootstrap";

import useLoadMoreOnScroll from "react-hook-pagination";
import { For } from "react-for-loop";

import ContentCard from "./ContentCard.jsx";

import * as ContentService from "../../services/axios/content"
import EditContentModal from "./EditContentModal.jsx";

import styles from '../../assets/css/content.module.css'

// this function is for spinny wheel loading thing
function Loader() {
    return (
        <div>
            <Spinner animation="border" role="status">
                <span className="sr-only">Loading...</span>
            </Spinner>
        </div>
    );
}

// react hooks for content list

export const ContentList = (props) => {
    const scroller = useRef();
    const [whichContent,] = useState(props.contentType)
    const [current,] = useState(props.current)
    const [data, setData] = useState([]);
    const [noRecs, setNoRecs] = useState(false);
    const {
        start,
        end,
        isFetching,
        doneFetching,
        setIsFetching,
        forceDonefetching
    } = useLoadMoreOnScroll({ fetchSize: 20, scroller: scroller, limit: 1000 });

    const fetchContents = async (start, end) => {
        const size = end - start;
        const page = end / size;
        var res = null;
        if (whichContent === 'recommended') {
            res = await ContentService.getRecommendedContent(page - 1, size);
            if (res.numberOfElements === 0) {
                res = await ContentService.getRecentContent(page - 1, size);
                setNoRecs(true);
            }
        }
        else if (whichContent === 'user') {
            res = await ContentService.getContentFromUser(props.username, page - 1, size);
        }
        else {
            res = await ContentService.getRecentContent(page - 1, size);
        }

        if (res.totalPages <= page) {
            forceDonefetching();
        }
        return res.content;
    };

    useEffect(() => {
        if (start !== end) {
            setIsFetching(true);
            fetchContents(start, end).then(contents => {
                setData([...data, ...contents]);
                setIsFetching(false);
            });
        }
    }, [start, end]);

    let header;
    if (whichContent === 'recommended' && !noRecs) {
        header = "Recommended contents";
    }
    else if (whichContent === 'recommended' && noRecs) {
        header = "You have no recomended contents so here's recent contents , follow a creator!";
    }
    else if (whichContent === 'user') {
        header = "Contents from " + props.username;
    } else {
        header = "Recent Contents";
    }

    return (
        <div >
            <h2 className={styles.modalHeader}>{header}</h2>
            <ListGroup ref={scroller} className={styles.listGroup}>
                <For data={data} itemRenderer={(data, idx) => (
                    <ListGroupItem key={idx} >
                        <ContentCard data={data} mediaType={data.mediaType} />
                        {current === true && <EditContentModal content={data} />}
                    </ListGroupItem>
                )}
                ></For>
                {isFetching && <Loader />}
                {doneFetching && <h4>Thats All!</h4>}
            </ListGroup>
        </div>
    )

}
