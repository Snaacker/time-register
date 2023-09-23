import {
    createContext,
    Dispatch,
    PropsWithChildren,
    SetStateAction,
    useState,
  } from "react";
  
  export function createCtx<A>(defaultValue: A, stateName:string, updateFunctionName:any) {
    type UpdateType = Dispatch<SetStateAction<typeof defaultValue>>;
    const defaultUpdate: UpdateType = () => defaultValue;
    const ctx = createContext({
      [stateName]: defaultValue,
      [updateFunctionName]: defaultUpdate,
    });
  
    function Provider(props: PropsWithChildren<{}>) {
      const [state, update] = useState(defaultValue);
      return <ctx.Provider value={{ state, update }} {...props} />;
    }
    return [ctx, Provider] as const;
  }