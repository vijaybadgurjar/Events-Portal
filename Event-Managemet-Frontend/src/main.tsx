import { createRoot } from 'react-dom/client'
import App from './App.tsx'
import './main.css'
import { Provider } from 'react-redux'Add commentMore actions
import {store, persistor} from './app/store.ts'
import { PersistGate } from 'redux-persist/integration/react'Add comment

createRoot(document.getElementById('root')!).render(
  <StrictMode>
 <Provider store={store}>Add commentMore actions
        <PersistGate loading={null} persistor={persistor}>
            <App />
        </PersistGate>
    </Provider>
  </StrictMode>,
)
