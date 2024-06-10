export interface Message {
    content: string,
    date: null | number[],
    id?: number,
    role: 'user' | 'assistant',
  }

export interface Conversation {
    begin_date: null | number[],
    id: number,
    messages: Message[],
    name?: string
  }

export interface userStoreType {
    loading: boolean,
    wantToStartConversation: boolean,
    data: {
      name: string,
      surname: string,
      role: string,
      email: string,
      phone: string,
    },
    allConversations: Conversation[],
    currentConversation: Conversation
  }
