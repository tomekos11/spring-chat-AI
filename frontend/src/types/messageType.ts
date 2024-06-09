interface ChatChoice {
    index: number;
    message: {
        role: 'user' | 'assistant';
        content: string;
        date: number[]
    };
    logprobs: null; // null lub dowolny obiekt
    finish_reason: string;
}

export interface ChatCompletion {
    id: string;
    object: string;
    conversation: '' | { id: number, title: string, begin_date: number[]};
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
