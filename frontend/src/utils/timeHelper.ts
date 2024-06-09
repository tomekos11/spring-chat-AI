export const getDate = (date: number[]) : string => {
  return `${date[2] < 10 ? '0' : ''}${date[2]}-${date[1] < 10 ? '0' : ''}${date[1]}-${date[0]}`;
};

export const getTime = (date: number[]) : string => {
  return `${date[3] < 10 ? '0' : ''}${date[3]}:${date[4] < 10 ? '0' : ''}${date[4]}`;
};

export const getTimeNow = () : number[] => {
  const now = new Date();
  const year = now.getFullYear();
  const month = now.getMonth() + 1;
  const day = now.getDate();
  const hours = now.getHours();
  const minutes = now.getMinutes();
  const seconds = now.getSeconds();

  return [year, month, day, hours, minutes, seconds];
};
