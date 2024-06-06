interface ChatChoice {
    index: number;
    message: {
        role: string;
        content: string;
    };
    logprobs: null; // null lub dowolny obiekt
    finish_reason: string;
}

export interface ChatCompletion {
    id: string;
    object: string;
    created: number;
    model: string;
    choices: ChatChoice[];
    usage: {
        prompt_tokens: number;
        completion_tokens: number;
        total_tokens: number;
    };
    system_fingerprint: null; // null lub dowolny obiekt
}
