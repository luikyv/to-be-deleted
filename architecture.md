# CIAM Architecture

```mermaid
classDiagram
    class AdapterContract {
        <<Interface>>
    }
    class IdpAdapter{
        +lookupAuthn()
    }
```