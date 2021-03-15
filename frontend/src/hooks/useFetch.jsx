import { useState, useEffect } from 'react';
import { authAxios } from '../services/axios';

export default function useFetch({ method, url, data = null, config = null }) {
    const [response, setResponse] = useState(null);
    const [error, setError] = useState("");
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            try {
                authAxios[method](url, JSON.parse(config), JSON.parse(data))
                    .then((res) => {
                        setResponse(res.data);
                    })
                    .finally(() => {
                        setIsLoading(false);
                    });
            } catch (err) {
                setError(err);
            }
        };

        fetchData();
    }, [method, url, data, config]);

    return { response, error, isLoading };
}