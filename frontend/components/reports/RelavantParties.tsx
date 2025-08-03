export function RelevantParties() {
  const victims = [
    {
      id: "#145",
      fullName: "Mattha, John",
      gender: "Male",
      nationality: "American",
      statement:
        "John is my father, the stolen items were his personal property...",
    },
  ];

  const witnesses = [
    {
      id: "#111",
      fullName: "Nguyen Van A",
      gender: "Male",
      nationality: "American",
      statement: "-",
    },
  ];

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const renderTable = (people: any[], title: string) => (
    <div className="mb-8 pb-6 border-b border-gray-200">
      <h4 className="font-semibold text-gray-900 mb-4">{title}</h4>
      <div className="overflow-x-auto border border-gray-300 rounded-lg">
        <table className="w-full">
          <thead className="bg-gray-100 border-b-2 border-gray-300">
            <tr>
              <th className="px-4 py-3 text-left text-sm font-semibold text-gray-900 border-r border-gray-300">
                ID
              </th>
              <th className="px-4 py-3 text-left text-sm font-semibold text-gray-900 border-r border-gray-300">
                Full Name
              </th>
              <th className="px-4 py-3 text-left text-sm font-semibold text-gray-900 border-r border-gray-300">
                Gender
              </th>
              <th className="px-4 py-3 text-left text-sm font-semibold text-gray-900 border-r border-gray-300">
                Nationality
              </th>
              <th className="px-4 py-3 text-left text-sm font-semibold text-gray-900">
                Statement / Description
              </th>
            </tr>
          </thead>
          <tbody>
            {people.map((person, index) => (
              <tr
                key={person.id}
                className={`${
                  index !== people.length - 1 ? "border-b border-gray-200" : ""
                }`}
              >
                <td className="px-4 py-3 text-sm text-gray-900 border-r border-gray-200">
                  {person.id}
                </td>
                <td className="px-4 py-3 text-sm text-gray-900 border-r border-gray-200">
                  {person.fullName}
                </td>
                <td className="px-4 py-3 text-sm text-gray-900 border-r border-gray-200">
                  {person.gender}
                </td>
                <td className="px-4 py-3 text-sm text-gray-900 border-r border-gray-200">
                  {person.nationality}
                </td>
                <td className="px-4 py-3 text-sm text-gray-900">
                  {person.statement}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );

  return (
    <div className="border-b-2 border-gray-200 pb-8 mb-8">
      <h3 className="text-blue-600 font-semibold mb-6 text-base">
        I. Relevant Parties
      </h3>
      {renderTable(victims, "A/ Victim")}
      {renderTable(witnesses, "B/ Witness")}
    </div>
  );
}
