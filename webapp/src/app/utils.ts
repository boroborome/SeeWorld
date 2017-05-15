
export function arrayToString(lst: string[]) : string {
  if (!lst || lst.length <= 0) {
    return '';
  }
  return lst.join('\n');
}

export function stringToArray(str: string) : any {
  if (!str || str.length <= 0) {
    return [];
  }
  return str.split('\n');
}


