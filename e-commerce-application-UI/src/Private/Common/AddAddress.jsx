import React, { useState } from "react";

const AddAddressForm = () => {
  const [formData, setFormData] = useState({
    streetAddress: "",
    streetAddressAdditional: "",
    city: "",
    state: "",
    pincode: "",
    addressType: "",
    contacts: [
      {
        name: "",
        email: "",
        phoneNumber: "",
        priority: "PRIMARY"
      },
      {
        name: "",
        email: "",
        phoneNumber: "",
        priority: "ADDITIONAL"
      }
    ]
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleContactChange = (index, e) => {
    const { name, value } = e.target;
    const updatedContacts = [...formData.contacts];
    updatedContacts[index][name] = value;
    setFormData({ ...formData, contacts: updatedContacts });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(formData);
    // Add logic to send form data to backend or perform other actions
  };

  return (
    <div className="max-w-xl mx-auto mt-8 p-6 bg-white rounded-lg shadow-md">
      <h2 className="text-2xl font-bold mb-4">Add Address and Contact</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        {/* Address Fields */}
        <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
          <div>
            <label className="block mb-1">Street Address:</label>
            <input
              type="text"
              name="streetAddress"
              value={formData.streetAddress}
              onChange={handleChange}
              className="w-full border border-gray-300 rounded-md p-2 focus:outline-none"
            />
          </div>
          <div>
            <label className="block mb-1">Street Address Additional:</label>
            <input
              type="text"
              name="streetAddressAdditional"
              value={formData.streetAddressAdditional}
              onChange={handleChange}
              className="w-full border border-gray-300 rounded-md p-2 focus:outline-none"
            />
          </div>
          <div>
            <label className="block mb-1">City:</label>
            <input
              type="text"
              name="city"
              value={formData.city}
              onChange={handleChange}
              className="w-full border border-gray-300 rounded-md p-2 focus:outline-none"
            />
          </div>
          <div>
            <label className="block mb-1">State:</label>
            <input
              type="text"
              name="state"
              value={formData.state}
              onChange={handleChange}
              className="w-full border border-gray-300 rounded-md p-2 focus:outline-none"
            />
          </div>
          <div>
            <label className="block mb-1">Pincode:</label>
            <input
              type="text"
              name="pincode"
              value={formData.pincode}
              onChange={handleChange}
              className="w-full border border-gray-300 rounded-md p-2 focus:outline-none"
            />
          </div>
          <div>
            <label className="block mb-1">Address Type:</label>
            <input
              type="text"
              name="addressType"
              value={formData.addressType}
              onChange={handleChange}
              className="w-full border border-gray-300 rounded-md p-2 focus:outline-none"
            />
          </div>
        </div>

        {/* Contact Fields */}
        {formData.contacts.map((contact, index) => (
          <div key={index} className="space-y-2">
            <h3 className="text-lg font-semibold mb-2">Contact {index + 1}</h3>
            <input
              type="text"
              name="name"
              value={contact.name}
              onChange={(e) => handleContactChange(index, e)}
              placeholder="Name"
              className="w-full border border-gray-300 rounded-md p-2 focus:outline-none"
            />
            <input
              type="email"
              name="email"
              value={contact.email}
              onChange={(e) => handleContactChange(index, e)}
              placeholder="Email"
              className="w-full border border-gray-300 rounded-md p-2 focus:outline-none"
            />
            <input
              type="tel"
              name="phoneNumber"
              value={contact.phoneNumber}
              onChange={(e) => handleContactChange(index, e)}
              placeholder="Phone Number"
              className="w-full border border-gray-300 rounded-md p-2 focus:outline-none"
            />
            <select
              name="priority"
              value={contact.priority}
              onChange={(e) => handleContactChange(index, e)}
              className="w-full border border-gray-300 rounded-md p-2 focus:outline-none"
            >
              <option value="PRIMARY">PRIMARY</option>
              <option value="ADDITIONAL">ADDITIONAL</option>
            </select>
          </div>
        ))}

        <button
          type="button"
          onClick={() =>
            setFormData({
              ...formData,
              contacts: [
                ...formData.contacts,
                { name: "", email: "", phoneNumber: "", priority: "ADDITIONAL" }
              ]
            })
          }
          className="bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600"
        >
          Add Another Contact
        </button>

        <button
          type="submit"
          className="bg-green-500 text-white py-2 px-4 rounded-md hover:bg-green-600"
        >
          Submit
        </button>
      </form>
    </div>
  );
};

export default AddAddressForm;
